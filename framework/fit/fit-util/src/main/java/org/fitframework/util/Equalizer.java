// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.util;

/**
 * 为对象提供比较器。
 *
 * @param <T> 表示待比较对象的类型。
 * @author 梁济时
 * @since 2020-07-24
 */
@FunctionalInterface
public interface Equalizer<T> {
    /**
     * 比较两个对象是否包含相同的数据。
     *
     * @param t1 表示待比较的第一个对象。
     * @param t2 表示待比较的第二个对象。
     * @return 若两个对象包含相同的数据，则为 {@code true}；否则为 {@code false}。
     */
    boolean equals(T t1, T t2);
}