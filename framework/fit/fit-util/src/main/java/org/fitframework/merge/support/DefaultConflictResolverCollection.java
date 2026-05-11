// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.merge.support;

import org.fitframework.inspection.Nonnull;
import org.fitframework.inspection.Validation;
import org.fitframework.merge.Conflict;
import org.fitframework.merge.ConflictResolver;
import org.fitframework.merge.ConflictResolverCollection;
import org.fitframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link ConflictResolverCollection} 的默认实现。
 *
 * @author 季聿阶
 * @since 2022-07-31
 */
public class DefaultConflictResolverCollection implements ConflictResolverCollection {
    private final Map<Class<?>, ConflictResolver<?, ?, ?>> resolvers = new HashMap<>();
    private ConflictResolver<Object, Object, Conflict<Object>> defaultResolver = new AbortConflictResolver<>();

    @Nonnull
    @Override
    public ConflictResolverCollection add(ConflictResolver<Object, Object, Conflict<Object>> resolver) {
        if (resolver != null) {
            this.defaultResolver = resolver;
        }
        return this;
    }

    @Nonnull
    @Override
    public <K, V, C extends Conflict<K>> ConflictResolverCollection add(Class<V> clazz,
            ConflictResolver<K, V, C> resolver) {
        Validation.notNull(clazz, "The class to register conflict resolver cannot be null.");
        if (resolver == null) {
            this.resolvers.remove(clazz);
        } else {
            this.resolvers.put(clazz, resolver);
        }
        return this;
    }

    @Nonnull
    @Override
    public <K, V, C extends Conflict<K>> ConflictResolver<K, V, C> get(Class<V> clazz) {
        Validation.notNull(clazz, "The class to get conflict resolver cannot be null.");
        ConflictResolver<K, V, C> actual = null;
        for (Map.Entry<Class<?>, ConflictResolver<?, ?, ?>> entry : this.resolvers.entrySet()) {
            if (entry.getKey().isAssignableFrom(clazz)) {
                actual = ObjectUtils.cast(entry.getValue());
                break;
            }
        }
        if (actual != null) {
            return actual;
        }
        return ObjectUtils.cast(this.defaultResolver);
    }
}
