// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.schedule.support;

import static org.fitframework.inspection.Validation.isTrue;

import org.fitframework.schedule.ExecutePolicy;

/**
 * 表示 {@link ExecutePolicy} 的抽象父类。
 *
 * @author 季聿阶
 * @since 2022-11-16
 */
public abstract class AbstractExecutePolicy implements ExecutePolicy {
    /**
     * 校验执行状态。
     *
     * @param status 表示待校验的执行状态的 {@link ExecutionStatus}。
     * @throws IllegalArgumentException 当 {@code status} 不为 {@link ExecutionStatus#SCHEDULING} 或 {@link
     * ExecutionStatus#EXECUTED} 时。
     */
    protected void validateExecutionStatus(ExecutionStatus status) {
        isTrue(status == ExecutionStatus.SCHEDULING || status == ExecutionStatus.EXECUTED,
                "The execution status must be SCHEDULING or EXECUTED. [status={0}]",
                status);
    }
}
