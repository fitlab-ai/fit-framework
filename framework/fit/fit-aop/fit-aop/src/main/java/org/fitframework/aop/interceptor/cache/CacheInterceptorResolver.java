// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.cache;

import org.fitframework.aop.interceptor.MethodInterceptor;
import org.fitframework.aop.interceptor.cache.support.CacheableInterceptorFactory;
import org.fitframework.aop.interceptor.cache.support.EvictCacheInterceptorFactory;
import org.fitframework.aop.interceptor.cache.support.PutCacheInterceptorFactory;
import org.fitframework.aop.interceptor.support.AbstractMethodInterceptorResolver;
import org.fitframework.ioc.BeanContainer;
import org.fitframework.ioc.BeanFactory;
import org.fitframework.ioc.BeanMetadata;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 解析缓存拦截事件提供者的解析器。
 *
 * @author 季聿阶
 * @since 2022-12-13
 */
public class CacheInterceptorResolver extends AbstractMethodInterceptorResolver {
    private final List<CacheInterceptorFactory> methodInterceptorFactories;

    /**
     * 使用指定的容器初始化 {@link CacheInterceptorResolver} 的新实例。
     *
     * @param container 表示容器的 {@link BeanContainer}。
     * @throws IllegalArgumentException 当 {@code container} 为 {@code null} 时。
     */
    public CacheInterceptorResolver(BeanContainer container) {
        this.methodInterceptorFactories = Arrays.asList(new CacheableInterceptorFactory(container),
                new PutCacheInterceptorFactory(container),
                new EvictCacheInterceptorFactory(container));
    }

    @Override
    public boolean eliminate(BeanMetadata metadata) {
        return false;
    }

    @Override
    public List<MethodInterceptor> resolve(BeanMetadata beanMetadata, Object bean) {
        List<MethodInterceptor> methodInterceptors = this.resolveMethodInterceptors(beanMetadata, bean);
        return this.matchPointcuts(methodInterceptors, bean);
    }

    private List<MethodInterceptor> resolveMethodInterceptors(BeanMetadata beanMetadata, Object bean) {
        List<MethodInterceptor> methodInterceptors = new ArrayList<>();
        Method[] methods = bean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            this.methodInterceptorFactories.stream()
                    .filter(factory -> factory.isInterceptMethod(method))
                    .findFirst()
                    .map(factory -> factory.create(this.getBeanFactory(beanMetadata), method))
                    .ifPresent(methodInterceptors::add);
        }
        return methodInterceptors;
    }

    private BeanFactory getBeanFactory(BeanMetadata beanMetadata) {
        return beanMetadata.container().factory(beanMetadata.type()).orElse(null);
    }
}
