// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.aspect.interceptor;

import org.fitframework.annotation.Scope;
import org.fitframework.aop.annotation.Aspect;
import org.fitframework.aop.interceptor.MethodInterceptor;
import org.fitframework.aop.interceptor.MethodInterceptorFactory;
import org.fitframework.aop.interceptor.support.AbstractMethodInterceptorResolver;
import org.fitframework.ioc.BeanContainer;
import org.fitframework.ioc.BeanFactory;
import org.fitframework.ioc.BeanMetadata;
import org.fitframework.util.TypeUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 解析 {@link Aspect} 注解的拦截事件提供者的解析器。
 *
 * @author 季聿阶
 * @author 郭龙飞
 * @since 2023-03-08
 */
public class AspectInterceptorResolver extends AbstractMethodInterceptorResolver {
    private final List<MethodInterceptorFactory> methodInterceptorFactories;

    private final Map<String, List<MethodInterceptor>> pluginMethodInterceptors = new HashMap<>();

    /**
     * 构造 {@link AspectInterceptorResolver} 的新实例。
     */
    public AspectInterceptorResolver() {
        this.methodInterceptorFactories = new ArrayList<>();
        this.methodInterceptorFactories.add(new AspectBeforeInterceptorFactory());
        this.methodInterceptorFactories.add(new AspectAroundInterceptorFactory());
        this.methodInterceptorFactories.add(new AspectAfterInterceptorFactory());
        this.methodInterceptorFactories.add(new AspectAfterReturningInterceptorFactory());
        this.methodInterceptorFactories.add(new AspectAfterThrowingInterceptorFactory());
    }

    @Override
    public boolean eliminate(BeanMetadata metadata) {
        return metadata.annotations().isAnnotationPresent(Aspect.class);
    }

    @Override
    public List<MethodInterceptor> resolve(BeanMetadata metadata, Object bean) {
        List<MethodInterceptor> methodInterceptors = this.getMethodInterceptors(metadata.container());
        return this.matchPointcuts(methodInterceptors, bean);
    }

    private List<MethodInterceptor> getMethodInterceptors(BeanContainer container) {
        if (this.pluginMethodInterceptors.containsKey(container.name())) {
            return this.pluginMethodInterceptors.get(container.name());
        }
        List<MethodInterceptor> methodInterceptors = this.resolveMethodInterceptors(container);
        this.pluginMethodInterceptors.put(container.name(), methodInterceptors);
        return methodInterceptors;
    }

    private List<MethodInterceptor> resolveMethodInterceptors(BeanContainer container) {
        List<MethodInterceptor> methodInterceptors = new ArrayList<>();
        List<BeanFactory> pluginFactories = container.factories()
                .stream()
                .filter(factory -> this.getScopeAspectFactories(factory, Scope.PLUGIN))
                .collect(Collectors.toList());
        List<BeanFactory> globalFactories = container.all()
                .stream()
                .filter(factory -> this.getScopeAspectFactories(factory, Scope.GLOBAL))
                .collect(Collectors.toList());
        pluginFactories.addAll(globalFactories);
        for (BeanFactory aspectBeanFactory : pluginFactories) {
            Method[] methods = TypeUtils.toClass(aspectBeanFactory.metadata().type()).getDeclaredMethods();
            for (Method method : methods) {
                this.methodInterceptorFactories.stream()
                        .filter(factory -> factory.isInterceptMethod(method))
                        .findFirst()
                        .map(factory -> factory.create(aspectBeanFactory, method))
                        .ifPresent(methodInterceptors::add);
            }
        }
        return methodInterceptors;
    }

    private boolean getScopeAspectFactories(BeanFactory factory, Scope expectedScope) {
        Aspect aspect = factory.metadata().annotations().getAnnotation(Aspect.class);
        return aspect != null && aspect.scope() == expectedScope;
    }
}
