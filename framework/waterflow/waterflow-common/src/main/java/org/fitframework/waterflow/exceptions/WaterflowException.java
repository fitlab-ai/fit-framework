// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.waterflow.exceptions;

import org.fitframework.waterflow.ErrorCodes;
import org.fitframework.exception.FitException;

import java.text.MessageFormat;

/**
 * 插件 Jobber 的异常基类。
 *
 * @author 陈镕希
 * @since 1.0
 */
public class WaterflowException extends FitException {
    /**
     * Represents additional arguments associated with this exception.
     * These arguments can be used for logging, debugging, or custom error handling.
     */
    private Object[] args;

    /**
     * 抛出Jobber异常。
     *
     * @param error 异常枚举的{@link ErrorCodes}。
     */
    public WaterflowException(ErrorCodes error) {
        super(error.getErrorCode(), error.getMessage());
    }

    /**
     * 抛出Jobber异常。
     *
     * @param error 异常枚举的{@link ErrorCodes}。
     * @param args 额外参数。
     */
    public WaterflowException(ErrorCodes error, Object... args) {
        super(error.getErrorCode(), MessageFormat.format(error.getMessage(), args));
        this.args = args;
    }

    /**
     * 抛出Jobber异常。
     *
     * @param cause 表示异常原因的 Throwable。
     * @param error 异常枚举的{@link ErrorCodes}。
     * @param args 额外参数。
     */
    public WaterflowException(Throwable cause, ErrorCodes error, Object... args) {
        super(error.getErrorCode(), MessageFormat.format(error.getMessage(), args), cause);
        this.args = args;
    }

    /**
     * Returns the additional arguments associated with this exception.
     *
     * @return An array of objects representing the arguments.
     */
    public Object[] getArgs() {
        return args;
    }
}
