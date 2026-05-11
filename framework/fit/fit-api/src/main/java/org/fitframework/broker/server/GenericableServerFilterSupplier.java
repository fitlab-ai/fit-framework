// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.server;

import org.fitframework.ioc.BeanContainer;

import java.util.List;

/**
 * 表示 {@link GenericableServerFilter} 列表的提供者。
 *
 * @author 李金绪
 * @since 2024-08-26
 */
public interface GenericableServerFilterSupplier {
    /**
     * 从指定容器中获取过滤器列表。
     *
     * @param container 表示指定容器的 {@link BeanContainer}。
     * @return 表示指定容器中的过滤器列表的 {@link List}{@code <}{@link GenericableServerFilter}{@code >}。
     */
    List<GenericableServerFilter> get(BeanContainer container);
}