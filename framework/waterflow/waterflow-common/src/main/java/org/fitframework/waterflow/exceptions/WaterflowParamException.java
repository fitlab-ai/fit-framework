// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.waterflow.exceptions;

import org.fitframework.waterflow.ErrorCodes;

/**
 * 参数错误抛出异常类。
 *
 * @author 陈镕希
 * @since 1.0
 */
public class WaterflowParamException extends WaterflowException {
    /**
     * 抛出Jobber参数错误异常。
     *
     * @param error 异常枚举的{@link ErrorCodes}。
     */
    public WaterflowParamException(ErrorCodes error) {
        super(error);
    }

    /**
     * 抛出Jobber参数错误异常。
     *
     * @param error 异常枚举的{@link ErrorCodes}。
     * @param args 额外参数。
     */
    public WaterflowParamException(ErrorCodes error, Object... args) {
        super(error, args);
    }
}
