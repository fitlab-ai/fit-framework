// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.cache;

import org.fitframework.aop.interceptor.MethodInvocation;
import org.fitframework.aop.interceptor.MethodJoinPoint;
import org.fitframework.cache.annotation.PutCache;
import org.fitframework.inspection.Nonnull;
import org.fitframework.inspection.Nullable;
import org.fitframework.ioc.BeanContainer;

import java.util.List;

/**
 * 表示 {@link PutCache} 的方法拦截器。
 *
 * @author 季聿阶
 * @since 2022-12-14
 */
public class PutCacheInterceptor extends AbstractCacheInterceptor {
    /**
     * 使用指定的容器、键生成器和缓存名称列表初始化 {@link PutCacheInterceptor} 的新实例。
     *
     * @param container 表示容器的 {@link BeanContainer}。
     * @param keyGenerator 表示键生成器的 {@link KeyGenerator}。
     * @param cacheNames 表示缓存名称列表的 {@link List}{@code <}{@link String}{@code >}。
     * @throws IllegalArgumentException 当任一参数为 {@code null} 时。
     */
    public PutCacheInterceptor(BeanContainer container, KeyGenerator keyGenerator, List<String> cacheNames) {
        super(container, keyGenerator, cacheNames);
    }

    @Nullable
    @Override
    public Object intercept(@Nonnull MethodJoinPoint methodJoinPoint) throws Throwable {
        MethodInvocation invocation = methodJoinPoint.getProxiedInvocation();
        CacheKey key = this.getKeyGenerator()
                .generate(invocation.getTarget(), invocation.getMethod(), invocation.getArguments());
        Object result = methodJoinPoint.proceed();
        this.getCacheInstances().forEach(instance -> instance.put(key, result));
        return result;
    }
}
