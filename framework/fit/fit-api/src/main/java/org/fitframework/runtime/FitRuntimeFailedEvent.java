// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.runtime;

import org.fitframework.event.Event;

/**
 * 当 FIT 运行时环境启动失败时引发的事件。
 *
 * @author 梁济时
 * @since 2022-11-30
 */
public interface FitRuntimeFailedEvent extends Event {
    /**
     * 获取启动失败的运行时。
     *
     * @return 表示启动失败的运行时的 {@link FitRuntime}。
     */
    FitRuntime runtime();

    /**
     * 获取启动失败的原因。
     *
     * @return 表示启动失败的原因的 {@link Throwable}。
     */
    Throwable cause();

    @Override
    default Object publisher() {
        return this.runtime();
    }
}
