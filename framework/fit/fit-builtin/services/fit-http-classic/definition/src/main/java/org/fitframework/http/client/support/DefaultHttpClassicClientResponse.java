// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.HttpResource;
import org.fitframework.http.client.HttpClassicClientResponse;
import org.fitframework.http.entity.Entity;
import org.fitframework.http.entity.EntityReadException;
import org.fitframework.http.entity.ObjectEntity;
import org.fitframework.http.entity.TextEntity;
import org.fitframework.http.entity.TextEventStreamEntity;
import org.fitframework.http.header.ContentType;
import org.fitframework.http.protocol.ClientResponse;
import org.fitframework.http.protocol.util.BodyUtils;
import org.fitframework.http.server.UnsupportedMediaTypeException;
import org.fitframework.http.support.AbstractHttpClassicResponse;
import org.fitframework.exception.ClientException;
import org.fitframework.util.LazyLoader;
import org.fitframework.util.ObjectUtils;
import org.fitframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * 表示 {@link HttpClassicClientResponse} 的默认实现。
 *
 * @author 季聿阶
 * @since 2022-11-25
 */
public class DefaultHttpClassicClientResponse<T> extends AbstractHttpClassicResponse
        implements HttpClassicClientResponse<T> {
    private final ClientResponse clientResponse;
    private final LazyLoader<byte[]> entityBytesLoader = new LazyLoader<>(this::actualEntityBytes);
    private final LazyLoader<Optional<Entity>> entityLoader = new LazyLoader<>(this::actualEntity);
    private final Type responseType;

    /**
     * 创建客户端的 Http 响应的默认实现对象。
     *
     * @param httpResource 表示 Http 的资源的 {@link HttpResource}。
     * @param clientResponse 表示客户端的 Http 响应的 {@link ClientResponse}。
     * @param responseType 表示响应类型的 {@link Type}。
     */
    public DefaultHttpClassicClientResponse(HttpResource httpResource, ClientResponse clientResponse,
            Type responseType) {
        super(httpResource,
                notNull(clientResponse, "The client response cannot be null.").startLine(),
                clientResponse.headers());
        this.clientResponse = clientResponse;
        this.responseType = responseType;
        this.commit();
    }

    @Override
    public Optional<Entity> entity() {
        return this.entityLoader.get();
    }

    @Override
    public byte[] entityBytes() {
        return this.entityBytesLoader.get();
    }

    @Override
    public Optional<ObjectEntity<T>> objectEntity() {
        return ObjectUtils.cast(this.entity());
    }

    @Override
    public Optional<TextEntity> textEntity() {
        return ObjectUtils.cast(this.entity());
    }

    @Override
    public Optional<TextEventStreamEntity> textEventStreamEntity() {
        return ObjectUtils.cast(this.entity());
    }

    private Optional<Entity> actualEntity() {
        Charset charset = this.contentType().flatMap(ContentType::charset).orElse(StandardCharsets.UTF_8);
        try {
            if (this.entityBytesLoader.isLoaded()) {
                byte[] bytes = this.entityBytes();
                return Optional.of(this.entitySerializer(this.responseType).deserializeEntity(bytes, charset, this));
            } else {
                InputStream inputStream = this.clientResponse.getBodyInputStream();
                return Optional.of(this.entitySerializer(this.responseType)
                        .deserializeEntity(inputStream, charset, this));
            }
        } catch (EntityReadException e) {
            throw new UnsupportedMediaTypeException(StringUtils.format("Unsupported media type. [mimeType='{0}']",
                    this.mimeTypeOrDefault().value()), e);
        }
    }

    private byte[] actualEntityBytes() {
        try {
            return BodyUtils.readBody(this.clientResponse.body(), this.headers());
        } catch (IOException e) {
            throw new ClientException("Failed to read body.", e);
        }
    }

    @Override
    public void close() {
        try {
            this.clientResponse.close();
        } catch (IOException e) {
            // Ignore
        }
    }
}
