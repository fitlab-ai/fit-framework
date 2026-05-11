// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.retry.condition;

import org.fitframework.retry.Condition;

/**
 * 表示限制重试次数的重试条件。
 *
 * @author 季聿阶
 * @since 2022-11-20
 */
public class TimesLimitedRetryCondition implements Condition {
    private final int maxAttemptTimes;

    public TimesLimitedRetryCondition(int maxAttemptTimes) {
        this.maxAttemptTimes = Math.max(maxAttemptTimes, 1);
    }

    @Override
    public boolean matches(int attemptTimes, long executionTimeMillis, Throwable cause) {
        return attemptTimes < this.maxAttemptTimes;
    }
}
