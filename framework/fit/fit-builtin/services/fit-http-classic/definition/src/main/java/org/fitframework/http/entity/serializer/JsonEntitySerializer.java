// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.entity.serializer;

import static org.fitframework.inspection.Validation.notNull;
import static org.fitframework.util.ObjectUtils.nullIf;

import org.fitframework.http.HttpMessage;
import org.fitframework.http.entity.EntityReadException;
import org.fitframework.http.entity.EntitySerializer;
import org.fitframework.http.entity.EntityWriteException;
import org.fitframework.http.entity.ObjectEntity;
import org.fitframework.http.entity.support.DefaultObjectEntity;
import org.fitframework.http.protocol.MessageHeaderNames;
import org.fitframework.inspection.Nonnull;
import org.fitframework.serialization.ObjectSerializer;
import org.fitframework.serialization.SerializationException;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * 表示消息体格式为 {@code 'application/json'} 的序列化器。
 *
 * @param <T> 表示反序列化后对应的数据结构类型的 {@link T}。
 * @author 季聿阶
 * @since 2022-10-11
 */
public class JsonEntitySerializer<T> implements EntitySerializer<ObjectEntity<T>> {
    private final Type type;
    private final ObjectSerializer jsonSerializer;

    /**
     * 使用指定的类型和 JSON 序列化器初始化 {@link JsonEntitySerializer} 的新实例。
     *
     * @param type 表示类型的 {@link Type}。
     * @param jsonSerializer 表示 JSON 序列化器的 {@link ObjectSerializer}。
     * @throws IllegalArgumentException 当 {@code jsonSerializer} 为 {@code null} 时。
     */
    public JsonEntitySerializer(Type type, ObjectSerializer jsonSerializer) {
        this.type = nullIf(type, Object.class);
        this.jsonSerializer = notNull(jsonSerializer, "The json serializer cannot be null.");
    }

    @Override
    public void serializeEntity(@Nonnull ObjectEntity<T> entity, Charset charset, OutputStream out) {
        try {
            this.jsonSerializer.serialize(entity.object(), charset, out);
        } catch (SerializationException e) {
            throw new EntityWriteException("Failed to serialize entity. [mimeType='application/json']", e);
        }
    }

    @Override
    public ObjectEntity<T> deserializeEntity(@Nonnull InputStream in, Charset charset, @Nonnull HttpMessage httpMessage,
            Type objectType) {
        try {
            Map<String, Object> context = new HashMap<>();
            httpMessage.headers()
                    .first(MessageHeaderNames.CONTENT_LENGTH)
                    .ifPresent(length -> context.put("length", Integer.parseInt(length)));
            T obj = this.jsonSerializer.deserialize(in, charset, this.type, context);
            return new DefaultObjectEntity<>(httpMessage, obj);
        } catch (SerializationException e) {
            throw new EntityReadException("Failed to deserialize message body. [mimeType='application/json']", e);
        }
    }
}
