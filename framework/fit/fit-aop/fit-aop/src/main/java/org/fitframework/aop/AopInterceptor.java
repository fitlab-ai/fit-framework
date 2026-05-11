// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.aop.interceptor.MethodInterceptor;
import org.fitframework.aop.interceptor.MethodInterceptorResolver;
import org.fitframework.aop.interceptor.async.AsyncInterceptorResolver;
import org.fitframework.aop.interceptor.cache.CacheInterceptorResolver;
import org.fitframework.aop.interceptor.support.MethodInterceptorComparator;
import org.fitframework.aop.proxy.AopProxyFactories;
import org.fitframework.aop.proxy.AopProxyFactory;
import org.fitframework.aop.proxy.InterceptSupport;
import org.fitframework.aop.proxy.support.DefaultInterceptSupport;
import org.fitframework.ioc.BeanContainer;
import org.fitframework.ioc.BeanFactory;
import org.fitframework.ioc.BeanMetadata;
import org.fitframework.ioc.lifecycle.bean.BeanLifecycle;
import org.fitframework.ioc.lifecycle.bean.BeanLifecycleInterceptor;
import org.fitframework.util.CollectionUtils;
import org.fitframework.util.LazyLoader;
import org.fitframework.util.TypeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

/**
 * {@link BeanLifecycleInterceptor} 的 AOP 实现。
 *
 * @author 季聿阶
 * @since 2022-05-01
 */
public class AopInterceptor implements BeanLifecycleInterceptor {
    private final BeanContainer container;
    private final LazyLoader<List<MethodInterceptorResolver>> methodInterceptorResolversLoader;
    private final List<AopProxyFactory> factories;
    private final MethodInterceptorComparator methodInterceptorComparator = new MethodInterceptorComparator();

    /**
     * 通过 Bean 容器来初始化 {@link AopInterceptor} 的新实例。
     *
     * @param container 表示 Bean 容器的 {@link BeanContainer}。
     */
    public AopInterceptor(BeanContainer container) {
        this.container = notNull(container, "The bean container cannot be null.");
        AopProxyFactories aopProxyFactories = this.container.lookup(AopProxyFactories.class)
                .map(BeanFactory::<AopProxyFactories>get)
                .orElseThrow(() -> new IllegalStateException("No aop proxy factories."));
        this.factories = aopProxyFactories.getAll();
        this.methodInterceptorResolversLoader = new LazyLoader<>(this::getMethodInterceptorResolvers);
    }

    @Override
    public boolean isInterceptionRequired(BeanMetadata metadata) {
        // 只有所有的方法拦截器的解析器都能解析，才能进行 AOP。
        return this.methodInterceptorResolversLoader.get().stream().noneMatch(resolver -> resolver.eliminate(metadata));
    }

    @Override
    public Object decorate(BeanLifecycle lifecycle, Object bean) {
        Object initializedBean = lifecycle.decorate(bean);
        if (CollectionUtils.isEmpty(this.methodInterceptorResolversLoader.get())) {
            return initializedBean;
        }
        List<MethodInterceptor> methodInterceptors = this.methodInterceptorResolversLoader.get()
                .stream()
                .map(resolver -> resolver.resolve(lifecycle.metadata(), bean))
                .flatMap(List::stream)
                .sorted(this.methodInterceptorComparator)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(methodInterceptors)) {
            return initializedBean;
        }
        InterceptSupport support = new DefaultInterceptSupport(TypeUtils.toClass(lifecycle.metadata().type()),
                () -> initializedBean,
                methodInterceptors);
        return this.createAopProxy(support);
    }

    private List<MethodInterceptorResolver> getMethodInterceptorResolvers() {
        List<MethodInterceptorResolver> resolvers = new ArrayList<>();
        resolvers.add(new AsyncInterceptorResolver());
        resolvers.add(new CacheInterceptorResolver(this.container));
        ServiceLoader<MethodInterceptorResolver> loader =
                ServiceLoader.load(MethodInterceptorResolver.class, this.getClass().getClassLoader());
        loader.forEach(resolvers::add);
        return resolvers;
    }

    private Object createAopProxy(InterceptSupport support) {
        for (AopProxyFactory proxyFactory : this.factories) {
            if (proxyFactory.support(support.getTargetClass())) {
                return proxyFactory.createProxy(support);
            }
        }
        return support.getTarget();
    }
}
