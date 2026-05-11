// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.client.http.websocket;

import org.fitframework.client.http.support.AbstractConnectionBuilder;
import org.fitframework.http.protocol.Protocol;

/**
 * 表示 WebSocket 链接的构建器。
 *
 * @author 季聿阶
 * @since 2024-05-07
 */
public class WebSocketConnectionBuilder extends AbstractConnectionBuilder {
    @Override
    public Protocol protocol() {
        return Protocol.WEB_SOCKET;
    }
}
