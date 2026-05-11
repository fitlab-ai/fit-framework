// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.lifecycle.bean;

/**
 * 为Bean提供初始化程序。
 *
 * @author 梁济时
 * @since 2022-04-28
 */
@FunctionalInterface
public interface BeanInitializer {
    /**
     * 初始化指定的Bean实例。
     *
     * @param bean 表示待初始化的Bean实例的 {@link Object}。
     */
    void initialize(Object bean);
}
