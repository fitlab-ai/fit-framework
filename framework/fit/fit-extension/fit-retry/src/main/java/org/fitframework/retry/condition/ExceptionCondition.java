// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.retry.condition;

import org.fitframework.inspection.Validation;
import org.fitframework.retry.Condition;
import org.fitframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 表示捕获异常的重试条件。
 *
 * @author 邬涨财
 * @since 2023-02-25
 */
public class ExceptionCondition implements Condition {
    private final List<Class<? extends Throwable>> capturedExceptions;

    public ExceptionCondition() {
        this(new ArrayList<>());
    }

    public ExceptionCondition(List<Class<? extends Throwable>> capturedExceptions) {
        this.capturedExceptions = Validation.notNull(capturedExceptions, "Captured exceptions can not be null");
    }

    @Override
    public boolean matches(int attemptTimes, long executionTimeMillis, Throwable cause) {
        return CollectionUtils.isEmpty(this.capturedExceptions) || this.capturedExceptions.stream()
                .filter(Objects::nonNull)
                .anyMatch(throwable -> throwable.isInstance(cause));
    }
}
