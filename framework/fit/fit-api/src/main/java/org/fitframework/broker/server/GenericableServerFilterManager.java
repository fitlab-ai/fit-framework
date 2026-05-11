// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.server;

import java.util.List;

/**
 * 表示过滤器的管理者。
 *
 * @author 李金绪
 * @since 2024-08-27
 */
public interface GenericableServerFilterManager {
    /**
     * 注册过滤器。
     *
     * @param filter 表示过滤器的 {@link GenericableServerFilter}。
     */
    void register(GenericableServerFilter filter);

    /**
     * 注销过滤器。
     *
     * @param filter 表示过滤器的 {@link GenericableServerFilter}。
     */
    void unregister(GenericableServerFilter filter);

    /**
     * 根获取所有的过滤器。
     *
     * @return 表示过滤器列表的 {@link List}{@code <}{@link GenericableServerFilter}{@code >}。
     */
    List<GenericableServerFilter> get();
}
