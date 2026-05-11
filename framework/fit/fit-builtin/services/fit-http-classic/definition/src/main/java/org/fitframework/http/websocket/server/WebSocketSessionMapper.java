// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.websocket.server;

import org.fitframework.http.server.HttpClassicServerRequest;
import org.fitframework.http.server.HttpClassicServerResponse;
import org.fitframework.http.server.handler.PropertyValueMapper;

import java.util.Map;

/**
 * 表示 WebSocket 中会话的映射器。
 *
 * @author 季聿阶
 * @since 2023-12-10
 */
public class WebSocketSessionMapper implements PropertyValueMapper {
    /** 表示存储在 WebSocket 握手请求中的会话主键。 */
    public static final String KEY = "FIT-WebSocket-Session";

    @Override
    public Object map(HttpClassicServerRequest request, HttpClassicServerResponse response,
            Map<String, Object> context) {
        return request.attributes().get(KEY).orElse(null);
    }
}
