// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.lifecycle.bean;

/**
 * 为Bean提供创建程序。
 *
 * @author 梁济时
 * @since 2022-04-28
 */
@FunctionalInterface
public interface BeanCreator {
    /**
     * 使用指定的初始化参数创建 Bean 实例。
     *
     * @param arguments 表示 Bean 的初始化参数的 {@link Object}{@code []}。
     * @return 表示新创建的 Bean 实例的 {@link Object}。
     */
    Object create(Object[] arguments);
}
