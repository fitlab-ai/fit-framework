// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.cache.support;

import org.fitframework.aop.interceptor.MethodInterceptor;
import org.fitframework.aop.interceptor.cache.EvictCacheInterceptor;
import org.fitframework.aop.interceptor.cache.KeyGenerator;
import org.fitframework.cache.annotation.EvictCache;
import org.fitframework.inspection.Nonnull;
import org.fitframework.ioc.BeanContainer;
import org.fitframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 表示 {@link EvictCache} 注解的方法拦截器工厂。
 *
 * @author 季聿阶
 * @since 2022-12-14
 */
public class EvictCacheInterceptorFactory extends AbstractCacheInterceptorFactory<EvictCache> {
    /**
     * 使用指定的容器初始化 {@link EvictCacheInterceptorFactory} 的新实例。
     *
     * @param container 表示容器的 {@link BeanContainer}。
     * @throws IllegalArgumentException 当 {@code container} 为 {@code null} 时。
     */
    public EvictCacheInterceptorFactory(BeanContainer container) {
        super(container, EvictCache.class);
    }

    @Override
    protected List<String> cacheInstanceNames(@Nonnull EvictCache annotation) {
        return Stream.of(annotation.name()).filter(StringUtils::isNotBlank).collect(Collectors.toList());
    }

    @Override
    protected String cacheKeyPattern(@Nonnull EvictCache annotation) {
        return annotation.key();
    }

    @Override
    protected MethodInterceptor create(BeanContainer container, KeyGenerator keyGenerator, List<String> cacheNames) {
        return new EvictCacheInterceptor(container, keyGenerator, cacheNames);
    }
}
