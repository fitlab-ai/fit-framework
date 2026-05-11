// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.schedule.support;

import static org.fitframework.inspection.Validation.greaterThan;
import static org.fitframework.inspection.Validation.isFalse;
import static org.fitframework.inspection.Validation.isTrue;

import org.fitframework.inspection.Nonnull;

import java.time.Instant;
import java.util.Optional;

/**
 * 表示固定频率的执行策略。
 *
 * @author 季聿阶
 * @since 2022-11-15
 */
public class FixedRateExecutePolicy extends AbstractExecutePolicy {
    private final long periodMillis;

    /**
     * 使用指定的执行周期来初始化 {@link FixedRateExecutePolicy} 的新实例。
     *
     * @param periodMillis 表示执行周期的毫秒数的 {@code long}。
     * @throws IllegalArgumentException 当 {@code periodMillis} 不是正数时。
     */
    public FixedRateExecutePolicy(long periodMillis) {
        this.periodMillis =
                greaterThan(periodMillis, 0, "The period millis must be positive. [period={0}]", periodMillis);
    }

    @Override
    public Optional<Instant> nextExecuteTime(@Nonnull Execution execution, @Nonnull Instant startTime) {
        this.validateExecutionStatus(execution.status());
        if (execution.status() == ExecutionStatus.SCHEDULING) {
            return Optional.of(startTime);
        } else {
            Optional<Instant> lastExecuteTime = execution.lastExecuteTime();
            isTrue(lastExecuteTime.isPresent(), "The last execute time must be present.");
            isFalse(lastExecuteTime.get().isBefore(startTime),
                    "The last execute time cannot before the start time. [lastExecuteTime={0}, startTime={1}]",
                    lastExecuteTime.get(),
                    startTime);
            return Optional.of(lastExecuteTime.get().plusMillis(this.periodMillis));
        }
    }
}
