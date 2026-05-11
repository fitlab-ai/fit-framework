// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.okhttp.websocket;

import org.fitframework.http.HttpResource;
import org.fitframework.http.client.HttpClassicClientResponse;
import org.fitframework.http.client.support.DefaultHttpClassicClientResponse;
import org.fitframework.http.protocol.ClientResponse;
import org.fitframework.http.websocket.client.WebSocketClassicListener;
import org.fitframework.inspection.Nonnull;
import org.fitframework.inspection.Nullable;
import org.fitframework.model.MultiValueMap;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * 表示 {@link WebSocketListener} 的适配器。
 *
 * @author 季聿阶
 * @since 2024-04-29
 */
public class WebSocketListenerAdapter extends WebSocketListener {
    private final HttpResource httpResource;
    private final WebSocketClassicListener listener;
    private final OkHttpWebSocketSession session;

    WebSocketListenerAdapter(HttpResource httpResource, WebSocketClassicListener listener,
            OkHttpWebSocketSession session) {
        this.httpResource = httpResource;
        this.listener = listener;
        this.session = session;
    }

    @Override
    public void onClosed(@Nonnull WebSocket webSocket, int code, @Nonnull String reason) {}

    @Override
    public void onClosing(@Nonnull WebSocket webSocket, int code, @Nonnull String reason) {
        this.listener.onClose(this.session, code, reason);
        this.session.close(code, reason);
    }

    @Override
    public void onFailure(@Nonnull WebSocket webSocket, @Nonnull Throwable cause, @Nullable Response response) {
        this.listener.onError(this.session, cause);
    }

    @Override
    public void onMessage(@Nonnull WebSocket webSocket, @Nonnull String text) {
        this.listener.onMessage(this.session, text);
    }

    @Override
    public void onMessage(@Nonnull WebSocket webSocket, @Nonnull ByteString bytes) {
        this.listener.onMessage(this.session, bytes.toByteArray());
    }

    @Override
    public void onOpen(@Nonnull WebSocket webSocket, @Nonnull Response response) {
        ClientResponse clientResponse = ClientResponse.create(response.code(),
                response.message(),
                MultiValueMap.create(response.headers().toMultimap()),
                null);
        HttpClassicClientResponse<Object> classicClientResponse =
                new DefaultHttpClassicClientResponse<>(this.httpResource, clientResponse, Object.class);
        this.session.setResponse(classicClientResponse);
        this.listener.onOpen(this.session);
    }
}
