// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler;

import static org.fitframework.http.protocol.MimeType.TEXT_PLAIN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.fitframework.http.HttpResource;
import org.fitframework.http.Serializers;
import org.fitframework.http.entity.Entity;
import org.fitframework.http.entity.EntitySerializer;
import org.fitframework.http.entity.support.DefaultNamedEntity;
import org.fitframework.http.protocol.MessageHeaderNames;
import org.fitframework.http.protocol.QueryCollection;
import org.fitframework.http.protocol.ReadableMessageBody;
import org.fitframework.http.protocol.RequestLine;
import org.fitframework.http.protocol.ServerRequest;
import org.fitframework.http.protocol.support.DefaultMessageHeaders;
import org.fitframework.http.server.HttpClassicServerRequest;
import org.fitframework.http.server.support.DefaultHttpClassicServerRequest;
import org.fitframework.util.MapBuilder;
import org.fitframework.util.ObjectUtils;

import java.io.InputStream;
import java.util.Map;

/**
 * 提供 {@link HttpClassicServerRequest} 的仿真实现。
 *
 * @author 白鹏坤
 * @since 2023-02-17
 */
public class MockHttpClassicServerRequest {
    /**
     * 表示 header 的键。
     */
    public static final String HEADER_KEY = "kk";

    /**
     * 表示 header 的值。
     */
    public static final String HEADER_VALUE = "hello";

    /**
     * 表示 uri 的值。
     */
    public static final String URI_KEY = "k1";

    /**
     * 表示 uri 的值。
     */
    public static final String URI_VALUE = "v1";

    private final DefaultHttpClassicServerRequest request;
    private final Entity entity;

    /**
     * 对经典的服务端的 Http 请求打桩。
     */
    public MockHttpClassicServerRequest() {
        RequestLine startLine = mock(RequestLine.class);
        DefaultMessageHeaders headers = new DefaultMessageHeaders();
        headers.add(HEADER_KEY, HEADER_VALUE);
        headers.add(MessageHeaderNames.CONTENT_TYPE, TEXT_PLAIN.value());
        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.startLine()).thenReturn(startLine);
        when(serverRequest.headers()).thenReturn(headers);
        ReadableMessageBody body = mock(ReadableMessageBody.class);
        when(serverRequest.body()).thenReturn(body);
        when(startLine.requestUri()).thenReturn("");
        when(startLine.queries()).thenReturn(QueryCollection.create(URI_KEY + "=" + URI_VALUE));
        HttpResource httpResource = mock(HttpResource.class);
        final Serializers serializers = mock(Serializers.class);
        final EntitySerializer<?> entitySerializer = mock(EntitySerializer.class);
        final Map map = MapBuilder.get().put(TEXT_PLAIN, entitySerializer).build();
        this.entity = mock(DefaultNamedEntity.class);
        when(httpResource.serializers()).thenReturn(serializers);
        when(serializers.entities()).thenReturn(map);
        when(entitySerializer.deserializeEntity((byte[]) any(), any(), any())).thenAnswer(ans -> this.entity);
        when(entitySerializer.deserializeEntity(ObjectUtils.<InputStream>cast(any()),
                any(),
                any())).thenAnswer(ans -> this.entity);
        this.request = new DefaultHttpClassicServerRequest(httpResource, serverRequest);
    }

    public DefaultHttpClassicServerRequest getRequest() {
        return this.request;
    }

    public Entity getEntity() {
        return this.entity;
    }
}
