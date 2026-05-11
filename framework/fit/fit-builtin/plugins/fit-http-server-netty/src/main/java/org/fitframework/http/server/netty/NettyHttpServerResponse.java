// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.netty;

import static org.fitframework.inspection.Validation.notNull;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpContent;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.LastHttpContent;
import org.fitframework.http.protocol.ConfigurableMessageHeaders;
import org.fitframework.http.protocol.ConfigurableStatusLine;
import org.fitframework.http.protocol.HttpVersion;
import org.fitframework.http.protocol.ServerResponse;
import org.fitframework.http.protocol.WritableMessageBody;
import org.fitframework.http.protocol.support.ServerResponseBody;
import org.fitframework.util.StringUtils;

import java.io.IOException;
import java.io.OutputStream;

/**
 * {@link ServerResponse} 的 Netty 实现。
 *
 * @author 季聿阶
 * @since 2022-07-08
 */
public class NettyHttpServerResponse implements ServerResponse {
    private final ConfigurableStatusLine startLine;
    private final ConfigurableMessageHeaders headers;
    private final ChannelHandlerContext ctx;
    private final boolean keepAlive;
    private final ServerResponseBody body;
    private boolean hasAddedClosedListener;
    private boolean isClosed;

    public NettyHttpServerResponse(ChannelHandlerContext ctx, NettyHttpServerRequest nettyRequest) {
        this.startLine = ConfigurableStatusLine.create(HttpVersion.HTTP_1_1, 0, StringUtils.EMPTY);
        this.headers = ConfigurableMessageHeaders.create();
        this.ctx = notNull(ctx, "The channel handler context cannot be null.");
        this.keepAlive = HttpUtil.isKeepAlive(nettyRequest.getNettyRequest());
        this.body = new ServerResponseBody(this);
    }

    @Override
    public ConfigurableStatusLine startLine() {
        return this.startLine;
    }

    @Override
    public ConfigurableMessageHeaders headers() {
        return this.headers;
    }

    @Override
    public WritableMessageBody body() {
        return this.body;
    }

    private void checkIfClosed() throws IOException {
        if (this.isClosed) {
            throw new IOException("The netty http server response has already been closed.");
        }
    }

    @Override
    public void writeStartLineAndHeaders() throws IOException {
        this.checkIfClosed();
        io.netty.handler.codec.http.HttpVersion httpVersion =
                io.netty.handler.codec.http.HttpVersion.valueOf(this.startLine().httpVersion().toString());
        HttpResponseStatus status = HttpResponseStatus.valueOf(this.startLine().statusCode());
        HttpResponse response = new DefaultHttpResponse(httpVersion, status);
        for (String headerName : this.headers().names()) {
            response.headers().set(headerName, this.headers().all(headerName));
        }
        if (this.keepAlive) {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        } else {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
        }
        this.ctx.writeAndFlush(response);
    }

    @Override
    public void writeBody(int b) throws IOException {
        this.writeBody(new byte[] {(byte) b});
    }

    @Override
    public void writeBody(byte[] bytes, int off, int len) throws IOException {
        this.checkIfClosed();
        HttpContent httpContent = new DefaultHttpContent(Unpooled.copiedBuffer(bytes, off, len));
        this.ctx.writeAndFlush(httpContent);
    }

    @Override
    public void flush() throws IOException {
        this.checkIfClosed();
        ChannelFuture channelFuture = this.ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        if (!this.keepAlive && !this.hasAddedClosedListener) {
            channelFuture.addListener(ChannelFutureListener.CLOSE);
            this.hasAddedClosedListener = true;
        }
    }

    @Override
    public OutputStream getBodyOutputStream() {
        return this.body;
    }

    @Override
    public boolean isActive() {
        return this.ctx.channel().isActive();
    }

    @Override
    public void closeChannel() {
        this.ctx.close();
    }

    @Override
    public void close() throws IOException {
        this.isClosed = true;
        this.body.close();
    }
}
