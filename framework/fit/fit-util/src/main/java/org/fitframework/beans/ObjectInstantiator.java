// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.beans;

import org.fitframework.beans.support.ReflectionFactoryInstantiator;

/**
 * 任意对象生成器。
 *
 * @param <T> 表示待实例化对象的类型的 {@link T}。
 * @author 季聿阶
 * @since 2022-05-10
 */
public interface ObjectInstantiator<T> {
    /**
     * 实例化一个对象。
     *
     * @return 表示待实例化的对象的 {@link T}。
     */
    T newInstance();

    /**
     * 创建一个标准的对象实例化器。
     *
     * @param type 表示待实例化的对象的类型的 {@link Class}{@code <}{@link T}{@code >}。
     * @param <T> 表示待实例化对象的类型的 {@link T}。
     * @return 表示指定类型的标准对象实例化器的 {@link ObjectInstantiator}{@code <}{@link T}{@code >}。
     * @throws IllegalArgumentException 当 {@code type} 为 {@code null} 时。
     */
    static <T> ObjectInstantiator<T> standard(Class<T> type) {
        return new ReflectionFactoryInstantiator<>(type);
    }
}
