// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.schedule;

/**
 * 表示执行过程中发生的异常。
 *
 * @author 季聿阶
 * @since 2022-12-26
 */
public class ExecuteException extends RuntimeException {
    /**
     * 使用指定的原因来初始化 {@link ExecuteException} 的新实例。
     *
     * @param cause 表示异常原因的 {@link Throwable}。
     */
    public ExecuteException(Throwable cause) {
        super(cause);
    }
}
