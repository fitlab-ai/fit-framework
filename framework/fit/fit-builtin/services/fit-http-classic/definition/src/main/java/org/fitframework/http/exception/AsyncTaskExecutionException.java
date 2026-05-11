// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.exception;

import org.fitframework.exception.ErrorCode;
import org.fitframework.exception.FitException;

/**
 * 表示异步任务执行异常。
 *
 * @author 王成
 * @since 2023-11-16
 */
@ErrorCode(AsyncTaskExecutionException.CODE)
public class AsyncTaskExecutionException extends FitException {
    /** 表示异步任务执行异常的异常码。 */
    public static final int CODE = 0x7F010010;

    /**
     * 通过异常信息来实例化 {@link AsyncTaskExecutionException}。
     *
     * @param message 表示异常信息的 {@link String}。
     */
    public AsyncTaskExecutionException(String message) {
        super(message);
    }

    /**
     * 通过异常原因来实例化 {@link AsyncTaskExecutionException}。
     *
     * @param cause 表示异常原因的 {@link Throwable}。
     */
    public AsyncTaskExecutionException(Throwable cause) {
        super(cause);
    }

    /**
     * 通过异常信息和异常原因来实例化 {@link AsyncTaskExecutionException}。
     *
     * @param message 表示异常信息的 {@link String}。
     * @param cause 表示异常原因的 {@link Throwable}。
     */
    public AsyncTaskExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
