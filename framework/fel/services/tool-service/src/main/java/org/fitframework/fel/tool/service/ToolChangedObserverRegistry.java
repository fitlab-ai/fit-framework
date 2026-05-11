// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.tool.service;

/**
 * 工具变更观察者注册表接口。
 *
 * @author 黄可欣
 * @since 2025-11-20
 */
public interface ToolChangedObserverRegistry {
    /**
     * 注册工具变更观察者。
     *
     * @param observer 待注册的工具变更观察者。
     */
    void register(ToolChangedObserver observer);

    /**
     * 注销工具变更观察者。
     *
     * @param observer 需要注销的工具变更观察者。
     */
    void unregister(ToolChangedObserver observer);
}
