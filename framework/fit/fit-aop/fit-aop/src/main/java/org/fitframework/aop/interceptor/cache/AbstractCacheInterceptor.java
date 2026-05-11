// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.cache;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.aop.interceptor.support.AbstractMethodInterceptor;
import org.fitframework.cache.Cache;
import org.fitframework.cache.CacheManager;
import org.fitframework.ioc.BeanContainer;
import org.fitframework.ioc.BeanFactory;
import org.fitframework.util.LazyLoader;
import org.fitframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 表示缓存方法拦截器的抽象父类。
 *
 * @author 季聿阶
 * @since 2023-04-07
 */
public abstract class AbstractCacheInterceptor extends AbstractMethodInterceptor {
    private final KeyGenerator keyGenerator;
    private final LazyLoader<List<Cache>> instancesLoader;

    AbstractCacheInterceptor(BeanContainer container, KeyGenerator keyGenerator, List<String> cacheNames) {
        notNull(container, "The bean container cannot be null.");
        this.keyGenerator = ObjectUtils.getIfNull(keyGenerator, KeyGenerator::params);
        List<String> actualCacheNames = ObjectUtils.getIfNull(cacheNames, ArrayList::new);
        this.instancesLoader = new LazyLoader<>(() -> container.factory(CacheManager.class)
                .map(BeanFactory::<CacheManager>get)
                .map(manager -> actualCacheNames.stream()
                        .map(manager::getInstance)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList()))
                .orElseGet(Collections::emptyList));
    }

    /**
     * 获取键的生成器。
     *
     * @return 表示键的生成器的 {@link KeyGenerator}。
     */
    protected KeyGenerator getKeyGenerator() {
        return this.keyGenerator;
    }

    /**
     * 获取缓存实例列表。
     *
     * @return 表示缓存实例列表的 {@link List}{@code <}{@link Cache}{@code >}。
     */
    protected List<Cache> getCacheInstances() {
        return this.instancesLoader.get();
    }
}
