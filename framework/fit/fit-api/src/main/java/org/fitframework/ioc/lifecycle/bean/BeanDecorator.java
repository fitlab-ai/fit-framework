// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.lifecycle.bean;

/**
 * 为Bean提供装饰程序。
 *
 * @author 梁济时
 * @since 2022-08-05
 */
@FunctionalInterface
public interface BeanDecorator {
    /**
     * 装饰指定Bean实例。
     *
     * @param bean 表示被装饰的Bean的 {@link Object}。
     * @return 表示装饰后的Bean的 {@link Object}。
     */
    Object decorate(Object bean);
}
