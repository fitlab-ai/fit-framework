// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.client.ioc;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.annotation.Fit;
import org.fitframework.annotation.Genericable;
import org.fitframework.aop.interceptor.MethodInterceptor;
import org.fitframework.aop.interceptor.MethodMatcher;
import org.fitframework.aop.proxy.AopProxyFactories;
import org.fitframework.aop.proxy.AopProxyFactory;
import org.fitframework.aop.proxy.InterceptSupport;
import org.fitframework.aop.proxy.support.DefaultInterceptSupport;
import org.fitframework.broker.client.BrokerClient;
import org.fitframework.broker.client.aop.DynamicRoutingInterceptor;
import org.fitframework.conf.runtime.CommunicationProtocol;
import org.fitframework.conf.runtime.SerializationFormat;
import org.fitframework.inspection.Nonnull;
import org.fitframework.ioc.BeanContainer;
import org.fitframework.ioc.BeanFactory;
import org.fitframework.ioc.BeanMetadata;
import org.fitframework.ioc.DependencyResolver;
import org.fitframework.ioc.DependencyResolvingResult;
import org.fitframework.ioc.annotation.AnnotationMetadata;
import org.fitframework.ioc.annotation.AnnotationMetadataResolver;
import org.fitframework.ioc.support.DefaultDependencyResolver;
import org.fitframework.util.LazyLoader;
import org.fitframework.util.TypeUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 支持动态路由的依赖解析器。
 *
 * @author 季聿阶
 * @since 2022-05-26
 */
public class DynamicRoutingDependencyResolver implements DependencyResolver {
    private final DependencyResolver resolver;
    private volatile AopProxyFactories aopFactories;

    /**
     * 直接实例化 {@link DynamicRoutingDependencyResolver}。
     */
    public DynamicRoutingDependencyResolver() {
        this(new DefaultDependencyResolver());
    }

    /**
     * 使用一个依赖解析器来实例化 {@link DynamicRoutingDependencyResolver}。
     *
     * @param resolver 表示依赖解析器的 {@link DependencyResolver}。
     */
    public DynamicRoutingDependencyResolver(DependencyResolver resolver) {
        this.resolver = notNull(resolver, "The dependency resolver cannot be null.");
    }

    @Override
    public DependencyResolvingResult resolve(@Nonnull BeanMetadata source, String name, @Nonnull Type type,
            @Nonnull AnnotationMetadata annotations) {
        if (this.ignoreDynamicRouting(source, type)) {
            return this.resolver.resolve(source, name, type, annotations);
        }
        List<AopProxyFactory> factories = this.getFactories(source.container());
        return new DynamicRoutingProxyDecorator(source, name, type, annotations, this.resolver, factories);
    }

    private List<AopProxyFactory> getFactories(BeanContainer container) {
        if (this.aopFactories == null) {
            this.aopFactories = container.lookup(AopProxyFactories.class)
                    .map(BeanFactory::<AopProxyFactories>get)
                    .orElseThrow(() -> new IllegalStateException("No aop proxy factories."));
        }
        return this.aopFactories.getAll();
    }

    private boolean ignoreDynamicRouting(BeanMetadata source, Type type) {
        Class<?> clazz = TypeUtils.toClass(type);
        if (this.hasMultipleInstances(clazz)) {
            // 如果依赖的类型是多实例的聚合类型，则不需要注入路由代理，直接返回解析结果。
            return true;
        }
        if (!clazz.isInterface()) {
            return true;
        }
        return !this.hasGenericableMethod(source, clazz);
    }

    private boolean hasMultipleInstances(Class<?> clazz) {
        return clazz == List.class || clazz == Map.class;
    }

    private boolean hasGenericableMethod(BeanMetadata source, Class<?> clazz) {
        AnnotationMetadataResolver metadataResolver = source.runtime().resolverOfAnnotations();
        for (Method method : clazz.getMethods()) {
            AnnotationMetadata annotations = metadataResolver.resolve(method);
            if (annotations.isAnnotationPresent(Genericable.class)) {
                return true;
            }
        }
        return false;
    }

    private static class DynamicRoutingProxyDecorator implements DependencyResolvingResult {
        private final String name;
        private final Class<?> clazz;
        private final boolean resolved;
        private final AopProxyFactory selectedFactory;
        private final AnnotationMetadata annotations;
        private final LazyLoader<BrokerClient> brokerClientLazyLoader;
        private final DependencyResolvingResult defaultResult;

        private DynamicRoutingProxyDecorator(BeanMetadata source, String name, Type type,
                AnnotationMetadata annotations, DependencyResolver resolver, List<AopProxyFactory> factories) {
            this.name = name;
            this.clazz = TypeUtils.toClass(type);
            int resolvedIndex = isResolved(factories, this.clazz);
            if (resolvedIndex >= 0) {
                this.defaultResult = DependencyResolvingResult.failure();
                this.resolved = true;
                this.selectedFactory = factories.get(resolvedIndex);
            } else {
                this.defaultResult = resolver.resolve(source, name, type, annotations);
                this.resolved = this.defaultResult.resolved();
                this.selectedFactory = null;
            }
            this.annotations = annotations;
            this.brokerClientLazyLoader = new LazyLoader<>(() -> source.container()
                    .lookup(BrokerClient.class)
                    .orElseThrow(() -> new IllegalStateException("No broker client."))
                    .get());
        }

        private static int isResolved(List<AopProxyFactory> factories, Class<?> clazz) {
            for (int i = 0; i < factories.size(); i++) {
                AopProxyFactory factory = factories.get(i);
                if (factory.support(clazz)) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        public boolean resolved() {
            return this.resolved;
        }

        @Override
        public Object get() {
            return this.getProxy().orElseGet(this.defaultResult::get);
        }

        private Optional<Object> getProxy() {
            return Optional.ofNullable(this.selectedFactory).map(factory -> {
                InterceptSupport support = this.constructInterceptSupport();
                return factory.createProxy(support);
            });
        }

        private InterceptSupport constructInterceptSupport() {
            MethodInterceptor methodInterceptor = this.constructMethodInterceptor();
            return new DefaultInterceptSupport(this.clazz, () -> null, Collections.singletonList(methodInterceptor));
        }

        private MethodInterceptor constructMethodInterceptor() {
            Fit annotation = this.annotations.getAnnotation(Fit.class);
            int retry = Optional.ofNullable(annotation).map(Fit::retry).orElse(0);
            int timeout = Optional.ofNullable(annotation).map(Fit::timeout).orElse(3000);
            TimeUnit timeoutUnit = Optional.ofNullable(annotation).map(Fit::timeunit).orElse(TimeUnit.MILLISECONDS);
            CommunicationProtocol protocol =
                    Optional.ofNullable(annotation).map(Fit::protocol).orElse(CommunicationProtocol.UNKNOWN);
            SerializationFormat format =
                    Optional.ofNullable(annotation).map(Fit::format).orElse(SerializationFormat.UNKNOWN);
            MethodInterceptor interceptor = new DynamicRoutingInterceptor(this.brokerClientLazyLoader,
                    this.name,
                    retry,
                    timeout,
                    timeoutUnit,
                    protocol,
                    format);
            interceptor.getPointCut().matchers().add(MethodMatcher.accessible());
            interceptor.getPointCut().add(this.clazz);
            return interceptor;
        }
    }
}
