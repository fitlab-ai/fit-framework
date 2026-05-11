// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc;

/**
 * 为IoC容器提供工厂Bean的定义。
 *
 * <p>将通过接口中泛型参数所提供的类型进行匹配。</p>
 *
 * @param <T> 表示Bean的类型。
 * @author 季聿阶
 * @since 2022-05-31
 */
public interface BeanSupplier<T> {
    /**
     * 获取Bean实例。
     *
     * @return 表示Bean实例的 {@link Object}。
     */
    T get();
}
