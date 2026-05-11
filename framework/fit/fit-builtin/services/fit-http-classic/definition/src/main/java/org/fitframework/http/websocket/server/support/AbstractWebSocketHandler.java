// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.websocket.server.support;

import static org.fitframework.inspection.Validation.notBlank;
import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.HttpResource;
import org.fitframework.http.server.HttpClassicServer;
import org.fitframework.http.websocket.server.WebSocketHandler;

/**
 * 表示 {@link WebSocketHandler} 的抽象实现。
 *
 * @author 季聿阶
 * @since 2023-12-09
 */
public abstract class AbstractWebSocketHandler implements WebSocketHandler {
    private final HttpClassicServer httpServer;
    private final String pathPattern;

    /**
     * 构造一个新的 {@link AbstractWebSocketHandler} 实例。
     *
     * @param info 表示包含 {@link WebSocketHandler} 信息的 {@link Info}。
     */
    public AbstractWebSocketHandler(Info info) {
        notNull(info, "The websocket handler info cannot be null.");
        this.httpServer = notNull(info.httpServer(), "The http server cannot be null.");
        this.pathPattern = notBlank(info.pathPattern(), "The path pattern cannot be blank.");
    }

    @Override
    public HttpResource httpResource() {
        return this.httpServer;
    }

    @Override
    public String pathPattern() {
        return this.pathPattern;
    }
}
