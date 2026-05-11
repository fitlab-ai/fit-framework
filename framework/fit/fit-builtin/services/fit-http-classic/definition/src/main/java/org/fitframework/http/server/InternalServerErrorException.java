// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server;

import org.fitframework.http.protocol.HttpResponseStatus;

/**
 * 表示服务器内部的异常。
 *
 * @author 季聿阶
 * @since 2023-05-17
 */
public class InternalServerErrorException extends HttpServerResponseException {
    /**
     * 通过异常消息来实例化 {@link InternalServerErrorException}。
     *
     * @param message 表示异常消息的 {@link String}。
     */
    public InternalServerErrorException(String message) {
        this(message, null);
    }

    /**
     * 通过异常消息和异常原因来实例化 {@link InternalServerErrorException}。
     *
     * @param message 表示异常消息的 {@link String}。
     * @param cause 表示异常原因的 {@link Throwable}。
     */
    public InternalServerErrorException(String message, Throwable cause) {
        super(HttpResponseStatus.INTERNAL_SERVER_ERROR, message, cause);
    }
}
