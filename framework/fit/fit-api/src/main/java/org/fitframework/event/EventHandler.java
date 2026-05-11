// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.event;

import org.fitframework.inspection.Nonnull;

/**
 * 为事件提供处理程序。
 *
 * @param <E> 表示事件数据的类型。
 * @author 梁济时
 * @since 2022-11-18
 */
public interface EventHandler<E extends Event> {
    /**
     * 处理事件。
     *
     * @param event 表示待处理的事件的 {@link Event}。
     */
    void handleEvent(@Nonnull E event);
}
