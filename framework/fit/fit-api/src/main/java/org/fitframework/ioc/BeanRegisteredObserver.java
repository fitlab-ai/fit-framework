// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc;

/**
 * 为Bean被注册提供观察者。
 *
 * @author 梁济时
 * @since 2022-06-30
 */
@FunctionalInterface
public interface BeanRegisteredObserver {
    /**
     * 当Bean被注册时被通知。
     *
     * @param metadata 表示新注册的Bean的元数据的 {@link BeanMetadata}。
     */
    void onBeanRegistered(BeanMetadata metadata);
}
