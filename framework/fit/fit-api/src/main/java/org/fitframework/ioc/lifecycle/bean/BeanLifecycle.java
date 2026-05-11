// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.lifecycle.bean;

import org.fitframework.ioc.BeanMetadata;

/**
 * 为Bean提供生命周期定义。
 *
 * @author 梁济时
 * @since 2022-04-28
 */
public interface BeanLifecycle extends BeanCreator, BeanDecorator, BeanInjector, BeanInitializer, BeanDestroyer {
    /**
     * 获取Bean的元数据。
     *
     * @return 表示Bean的元数据的 {@link BeanMetadata}。
     */
    BeanMetadata metadata();
}
