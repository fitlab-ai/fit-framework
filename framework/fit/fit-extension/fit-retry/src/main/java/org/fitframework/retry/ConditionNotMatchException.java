// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.retry;

/**
 * 表示重试条件不满足的异常。
 *
 * @author 季聿阶
 * @since 2022-11-17
 */
public class ConditionNotMatchException extends RetryException {
    public ConditionNotMatchException(int attemptTimes, Throwable cause) {
        super(attemptTimes, cause);
    }
}
