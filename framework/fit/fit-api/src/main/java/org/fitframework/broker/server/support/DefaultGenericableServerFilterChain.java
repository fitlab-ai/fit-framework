// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.server.support;

import org.fitframework.broker.server.DoGenericableServerFilterException;
import org.fitframework.broker.server.GenericableServerFilter;
import org.fitframework.broker.server.GenericableServerFilterChain;
import org.fitframework.inspection.Validation;
import org.fitframework.util.wildcard.Pattern;

import java.util.List;
import java.util.Optional;

/**
 * 表示 {@link GenericableServerFilterChain} 的默认实现。
 *
 * @author 李金绪
 * @since 2024-08-27
 */
public class DefaultGenericableServerFilterChain implements GenericableServerFilterChain {
    private static final char SEPARATOR = '.';

    private final List<GenericableServerFilter> filters;
    private final String genericableId;
    private int index;

    /**
     * 使用指定的服务标识和过滤器列表来初始化 {@link DefaultGenericableServerFilterChain} 的新实例。
     *
     * @param genericableId 表示服务标识的 {@link String}。
     * @param filters 表示过滤器列表的 {@link List}{@code <}{@link GenericableServerFilter}{@code >}。
     * @throws IllegalArgumentException 当 {@code genericableId} 为 {@code null} 或空白字符串，或当 {@code filters} 为 {@code null} 时。
     */
    public DefaultGenericableServerFilterChain(String genericableId, List<GenericableServerFilter> filters) {
        this.genericableId = Validation.notBlank(genericableId, "The genericable id cannot be blank.");
        this.filters = Validation.notNull(filters, "The genericable server filters cannot be null.");
        this.index = -1;
    }

    @Override
    public void doFilter(Object[] args) throws DoGenericableServerFilterException {
        Optional<GenericableServerFilter> opNextFilter = this.findNextFilter(this.genericableId);
        if (opNextFilter.isPresent()) {
            opNextFilter.get().doFilter(genericableId, args, this);
        }
    }

    private Optional<GenericableServerFilter> findNextFilter(String genericableId) {
        for (int i = this.index + 1; i < this.filters.size(); i++) {
            GenericableServerFilter nextFilter = this.filters.get(i);
            boolean isMismatch = nextFilter.mismatchPatterns()
                    .stream()
                    .map(pattern -> Pattern.forPath(pattern, SEPARATOR))
                    .anyMatch(pattern -> pattern.matches(genericableId));
            if (isMismatch) {
                continue;
            }
            for (String pattern : nextFilter.matchPatterns()) {
                boolean isMatches = Pattern.forPath(pattern, SEPARATOR).matches(genericableId);
                if (isMatches) {
                    this.index = i;
                    return Optional.of(nextFilter);
                }
            }
        }
        return Optional.empty();
    }
}