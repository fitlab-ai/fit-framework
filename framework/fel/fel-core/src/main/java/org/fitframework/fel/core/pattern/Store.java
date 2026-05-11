// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.pattern;

/**
 * 表示持久化存储的实体。
 *
 * @param <D> 表示待添加数据的泛型。
 * @author 易文渊
 * @since 2024-08-06
 */
@FunctionalInterface
public interface Store<D> extends Pattern<D, Void> {
    /**
     * 添加数据到存储中。
     *
     * @param data 表示待添加数据的 {@link D}。
     */
    void persistent(D data);

    @Override
    default Void invoke(D data) {
        this.persistent(data);
        return null;
    }
}