// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client;

import static org.fitframework.inspection.Validation.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.fitframework.http.Serializers;
import org.fitframework.http.entity.ObjectEntity;
import org.fitframework.http.entity.TextEntity;
import org.fitframework.http.protocol.HttpRequestMethod;
import org.fitframework.http.websocket.Session;
import org.fitframework.http.websocket.client.WebSocketClassicListener;
import org.fitframework.flowable.Choir;
import org.fitframework.value.ValueFetcher;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * {@link HttpClassicClient} 的实现类
 *
 * @author 杭潇
 * @since 2023-02-20
 */
public class HttpClassicClientImplement implements HttpClassicClient {
    /**
     * 创建 Http 客户端对象。
     */
    public HttpClassicClientImplement() {}

    @Override
    public Serializers serializers() {
        return null;
    }

    @Override
    public ValueFetcher valueFetcher() {
        return null;
    }

    @Override
    public HttpClassicClientRequest createRequest(HttpRequestMethod method, String url) {
        return null;
    }

    @Override
    public Session createWebSocketSession(String url, WebSocketClassicListener listener) {
        return null;
    }

    @Override
    public HttpClassicClientResponse<Object> exchange(HttpClassicClientRequest request) {
        return this.exchange(request, Object.class);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public <T> HttpClassicClientResponse<T> exchange(HttpClassicClientRequest request, Type responseType) {
        notNull(request, "The http classic request to exchange cannot be null.");
        HttpClassicClientResponse response = mock(HttpClassicClientResponse.class);
        ObjectEntity objectEntity = mock(ObjectEntity.class);
        when(objectEntity.object()).thenReturn(24);
        Optional<ObjectEntity> optionalObjectEntity = Optional.of(objectEntity);
        when(response.objectEntity()).thenReturn(optionalObjectEntity);
        TextEntity textEntity = mock(TextEntity.class);
        when(textEntity.content()).thenReturn("finishTextEntity");
        Optional<TextEntity> optionalTextEntity = Optional.of(textEntity);
        when(response.textEntity()).thenReturn(optionalTextEntity);
        when(response.entityBytes()).thenReturn("finishTextEntity".getBytes(StandardCharsets.UTF_8));
        int statusCode = request.exchange(responseType).statusCode();
        when(response.statusCode()).thenReturn(statusCode);
        return response;
    }

    @Override
    public Choir<Object> exchangeStream(HttpClassicClientRequest request) {
        return null;
    }

    @Override
    public <T> Choir<T> exchangeStream(HttpClassicClientRequest request, Type responseType) {
        return null;
    }
}
