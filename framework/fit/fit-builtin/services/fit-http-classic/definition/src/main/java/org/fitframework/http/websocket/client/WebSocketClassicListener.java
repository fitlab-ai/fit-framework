// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.websocket.client;

import org.fitframework.http.websocket.Session;

/**
 * 表示 WebSocket 客户端的监听器。
 *
 * @author 季聿阶
 * @since 2024-04-29
 */
public interface WebSocketClassicListener {
    /**
     * 当一个新的 WebSocket 会话打开时的处理方法。
     *
     * @param session 表示 WebSocket 会话的 {@link Session}。
     */
    void onOpen(Session session);

    /**
     * 当收到 WebSocket 文本消息时的处理方法。
     *
     * @param session 表示 WebSocket 会话的 {@link Session}。
     * @param message 表示收到的 WebSocket 文本消息的 {@link String}。
     */
    void onMessage(Session session, String message);

    /**
     * 当收到 WebSocket 二进制消息时的处理方法。
     *
     * @param session 表示 WebSocket 会话的 {@link Session}。
     * @param message 表示收到的 WebSocket 二进制消息的 {@link String}。
     */
    void onMessage(Session session, byte[] message);

    /**
     * 当 WebSocket 会话关闭时的处理方法。
     *
     * @param session 表示 WebSocket 会话的 {@link Session}。
     * @param code 表示 WebSocket 会话关闭的状态码的 {@code int}。
     * @param reason 表示 WebSocket 会话关闭的原因的 {@link String}。
     */
    void onClose(Session session, int code, String reason);

    /**
     * 当 WebSocket 会话过程发生异常时的处理方法。
     *
     * @param session 表示 WebSocket 会话的 {@link Session}。
     * @param cause 表示会话过程发生异常的原因的 {@link Throwable}。
     */
    void onError(Session session, Throwable cause);
}
