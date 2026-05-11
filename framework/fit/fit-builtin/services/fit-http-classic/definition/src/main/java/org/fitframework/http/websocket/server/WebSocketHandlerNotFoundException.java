// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.websocket.server;

import org.fitframework.http.protocol.HttpResponseStatus;
import org.fitframework.http.server.HttpServerResponseException;

/**
 * 表示无法找到合适的 {@link WebSocketHandler} 的异常。
 *
 * @author 季聿阶
 * @since 2022-07-26
 */
public class WebSocketHandlerNotFoundException extends HttpServerResponseException {
    /**
     * 通过异常消息来实例化 {@link WebSocketHandlerNotFoundException}。
     *
     * @param message 表示异常消息的 {@link String}。
     */
    public WebSocketHandlerNotFoundException(String message) {
        this(message, null);
    }

    /**
     * 通过异常消息和异常原因来实例化 {@link WebSocketHandlerNotFoundException}。
     *
     * @param message 表示异常消息的 {@link String}。
     * @param cause 表示异常原因的 {@link Throwable}。
     */
    public WebSocketHandlerNotFoundException(String message, Throwable cause) {
        super(HttpResponseStatus.NOT_FOUND, message, cause);
    }
}
