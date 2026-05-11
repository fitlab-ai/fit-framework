// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.exception;

import org.fitframework.exception.ErrorCode;
import org.fitframework.exception.FitException;

/**
 * 表示异步任务查询失败。
 *
 * @author 王成
 * @since 2023-11-16
 */
@ErrorCode(AsyncTaskNotFoundException.CODE)
public class AsyncTaskNotFoundException extends FitException {
    /** 表示获取异常任务失败的异常码。 */
    public static final int CODE = 0x7F010008;

    /**
     * 通过异常信息来实例化 {@link AsyncTaskNotFoundException}。
     *
     * @param message 表示异常信息的 {@link String}。
     */
    public AsyncTaskNotFoundException(String message) {
        super(message);
    }

    /**
     * 通过异常原因来实例化 {@link AsyncTaskNotFoundException}。
     *
     * @param cause 表示异常原因的 {@link Throwable}。
     */
    public AsyncTaskNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * 通过异常信息和异常原因来实例化 {@link AsyncTaskNotFoundException}。
     *
     * @param message 表示异常信息的 {@link String}。
     * @param cause 表示异常原因的 {@link Throwable}。
     */
    public AsyncTaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
