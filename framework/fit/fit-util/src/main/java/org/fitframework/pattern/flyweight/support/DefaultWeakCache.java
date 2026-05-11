// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.pattern.flyweight.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.pattern.flyweight.WeakCache;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 为 {@link WeakCache} 提供默认实现。
 *
 * @author 梁济时
 * @since 2023-02-09
 */
public class DefaultWeakCache<K, F, V extends F> implements WeakCache<K, F> {
    private final Map<K, WeakReference<V>> cache;
    private final Function<K, V> factory;
    private final Function<V, K> indexer;

    /**
     * 使用通过键创建缓存对象的方法及缓存对象的索引方法初始化 {@link DefaultWeakCache} 类的新实例。
     *
     * @param factory 表示用以通过键创建缓存对象的方法的 {@link Function}。
     * @param indexer 表示用以通过缓存对象实例获取用以检索的键的方法的 {@link Function}。
     * @throws IllegalArgumentException {@code factory} 或 {@code indexer} 为 {@code null}。
     */
    public DefaultWeakCache(Function<K, V> factory, Function<V, K> indexer) {
        this.factory = notNull(factory, "The factory to create instance with specific key cannot be null.");
        this.indexer = notNull(indexer, "The indexer for cached instance cannot be null.");
        this.cache = new HashMap<>();
    }

    @Override
    public F get(K key) {
        V instance;
        WeakReference<V> reference = this.cache.get(key);
        if (reference == null || (instance = reference.get()) == null) {
            instance = this.factory.apply(key);
            reference = new WeakReference<>(instance);
            this.cache.put(this.indexer.apply(instance), reference);
        }
        return instance;
    }
}
