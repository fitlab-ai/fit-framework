// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.websocket.client.support;

import org.fitframework.http.websocket.Session;
import org.fitframework.http.websocket.client.WebSocketClassicListener;

/**
 * 表示 {@link WebSocketClassicListener} 的空实现。
 *
 * @author 季聿阶
 * @since 2024-05-04
 */
public class EmptyWebSocketListener implements WebSocketClassicListener {
    @Override
    public void onOpen(Session session) {}

    @Override
    public void onMessage(Session session, String message) {}

    @Override
    public void onMessage(Session session, byte[] message) {}

    @Override
    public void onClose(Session session, int code, String reason) {}

    @Override
    public void onError(Session session, Throwable cause) {}
}
