// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker;

import org.fitframework.exception.FitException;

/**
 * 表示通过异常码、异常消息等属性创建 FIT 系统异常的构建器。
 *
 * @author 何天放
 * @since 2024-05-11
 */
public interface FitExceptionCreator {
    /**
     * 创建 FIT 系统异常。
     *
     * @param exceptionInfo 表示异常元数据的 {@link ExceptionInfo}。
     * @return 表示所创建 FIT 系统异常的 {@link FitException}。
     */
    FitException buildException(ExceptionInfo exceptionInfo);
}
