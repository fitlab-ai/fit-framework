// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler;

import org.fitframework.http.protocol.HttpResponseStatus;
import org.fitframework.http.server.HttpServerResponseException;

/**
 * 表示 Http 请求映射的异常。
 *
 * @author 季聿阶
 * @since 2022-07-08
 */
public class RequestMappingException extends HttpServerResponseException {
    /**
     * 通过异常消息来实例化 {@link RequestMappingException}。
     *
     * @param message 表示异常消息的 {@link String}。
     */
    public RequestMappingException(String message) {
        this(message, null);
    }

    /**
     * 通过异常消息和异常原因来实例化 {@link RequestMappingException}。
     *
     * @param message 表示异常消息的 {@link String}。
     * @param cause 表示异常原因的 {@link Throwable}。
     */
    public RequestMappingException(String message, Throwable cause) {
        super(HttpResponseStatus.BAD_REQUEST, message, cause);
    }
}
