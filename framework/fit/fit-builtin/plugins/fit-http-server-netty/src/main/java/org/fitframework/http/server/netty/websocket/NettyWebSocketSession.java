// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.netty.websocket;

import static org.fitframework.inspection.Validation.notNull;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.fitframework.http.server.HttpClassicServerRequest;
import org.fitframework.http.websocket.Session;
import org.fitframework.http.websocket.server.WebSocketSessionMapper;
import org.fitframework.http.websocket.support.AbstractSession;

/**
 * 表示 {@link Session} 的 Netty 的实现。
 *
 * @author 季聿阶
 * @since 2023-12-07
 */
public class NettyWebSocketSession extends AbstractSession {
    private final ChannelHandlerContext context;
    private final HttpClassicServerRequest request;

    public NettyWebSocketSession(ChannelHandlerContext context, HttpClassicServerRequest request) {
        this.context = notNull(context, "The netty channel context cannot be null.");
        this.request = notNull(request, "The http classic request cannot be null.");
        this.request.attributes().set(WebSocketSessionMapper.KEY, this);
    }

    @Override
    public HttpClassicServerRequest getHandshakeMessage() {
        return this.request;
    }

    @Override
    public void send(String text) {
        this.context.channel().writeAndFlush(new TextWebSocketFrame(text));
    }

    @Override
    public void send(byte[] bytes) {
        this.context.channel().writeAndFlush(new BinaryWebSocketFrame(Unpooled.wrappedBuffer(bytes)));
    }

    @Override
    protected void close0(int code, String reason) {
        this.context.channel().writeAndFlush(new CloseWebSocketFrame(code, reason));
    }
}
