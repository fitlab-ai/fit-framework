// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.event;

import org.fitframework.broker.LocalExecutor;
import org.fitframework.broker.UniqueFitableId;

/**
 * 表示本地执行器注册完毕的观察者。
 *
 * @author 季聿阶
 * @since 2023-03-25
 */
@FunctionalInterface
public interface LocalExecutorRegisteredObserver {
    /**
     * 当本地执行器注册完毕时触发。
     *
     * @param id 表示注册的本地执行器对应的服务实现的唯一标识的 {@link UniqueFitableId}。
     * @param executor 表示被注册的本地执行器的 {@link LocalExecutor}。
     */
    void onLocalExecutorRegistered(UniqueFitableId id, LocalExecutor executor);
}
