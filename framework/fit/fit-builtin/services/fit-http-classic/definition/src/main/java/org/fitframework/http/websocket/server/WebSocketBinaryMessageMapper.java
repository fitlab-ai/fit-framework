// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.websocket.server;

import org.fitframework.http.server.HttpClassicServerRequest;
import org.fitframework.http.server.HttpClassicServerResponse;
import org.fitframework.http.server.handler.PropertyValueMapper;

import java.util.Map;

/**
 * 表示 WebSocket 中二进制消息的映射器。
 *
 * @author 季聿阶
 * @since 2023-12-11
 */
public class WebSocketBinaryMessageMapper implements PropertyValueMapper {
    /** 表示在自定义上下文中二进制消息的主键。 */
    public static final String KEY = "FIT-WebSocket-Binary-Message";

    @Override
    public Object map(HttpClassicServerRequest request, HttpClassicServerResponse response,
            Map<String, Object> context) {
        if (context == null) {
            return null;
        }
        Object binaryMessage = context.get(KEY);
        if (binaryMessage instanceof byte[]) {
            return binaryMessage;
        }
        return null;
    }
}
