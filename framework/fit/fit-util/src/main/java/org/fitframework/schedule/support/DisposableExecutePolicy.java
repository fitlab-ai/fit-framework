// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.schedule.support;

import org.fitframework.inspection.Nonnull;
import org.fitframework.schedule.ExecutePolicy;

import java.time.Instant;
import java.util.Optional;

/**
 * 表示一次性的执行策略。
 *
 * @author 季聿阶
 * @since 2022-11-15
 */
public class DisposableExecutePolicy extends AbstractExecutePolicy {
    /** 表示一次性执行策略的单例的 {@link ExecutePolicy}。 */
    public static final ExecutePolicy INSTANCE = new DisposableExecutePolicy();

    private DisposableExecutePolicy() {}

    @Override
    public Optional<Instant> nextExecuteTime(@Nonnull Execution execution, @Nonnull Instant startTime) {
        this.validateExecutionStatus(execution.status());
        if (execution.status() == ExecutionStatus.SCHEDULING) {
            return Optional.of(startTime);
        }
        return Optional.empty();
    }
}
