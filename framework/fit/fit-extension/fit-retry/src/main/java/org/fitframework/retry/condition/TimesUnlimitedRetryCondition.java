// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.retry.condition;

import org.fitframework.retry.Condition;

/**
 * 表示无限次重试的重试条件。
 *
 * @author 季聿阶
 * @since 2022-11-20
 */
public class TimesUnlimitedRetryCondition implements Condition {
    @Override
    public boolean matches(int attemptTimes, long executionTimeMillis, Throwable cause) {
        return true;
    }
}
