// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.entity.serializer;

import org.fitframework.http.HttpMessage;
import org.fitframework.http.entity.EntityReadException;
import org.fitframework.http.entity.EntitySerializer;
import org.fitframework.http.entity.EntityWriteException;
import org.fitframework.http.entity.MultiValueEntity;
import org.fitframework.http.entity.support.DefaultMultiValueEntity;
import org.fitframework.http.protocol.util.BodyUtils;
import org.fitframework.http.util.HttpUtils;
import org.fitframework.inspection.Nonnull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * 表示消息体格式为 {@code 'application/x-www-form-urlencoded'} 的序列化器。
 *
 * @author 季聿阶
 * @since 2022-10-12
 */
public class FormUrlEncodedEntitySerializer implements EntitySerializer<MultiValueEntity> {
    /** 表示 {@link FormUrlEncodedEntitySerializer} 的单例实现。 */
    public static final EntitySerializer<MultiValueEntity> INSTANCE = new FormUrlEncodedEntitySerializer();

    private FormUrlEncodedEntitySerializer() {}

    @Override
    public void serializeEntity(@Nonnull MultiValueEntity entity, Charset charset, OutputStream out) {
        try {
            out.write(entity.toString().getBytes(charset));
        } catch (IOException e) {
            throw new EntityWriteException("Failed to serialize entity. [mimeType='application/x-www-form-urlencoded']",
                    e);
        }
    }

    @Override
    public MultiValueEntity deserializeEntity(@Nonnull InputStream in, Charset charset,
            @Nonnull HttpMessage httpMessage, Type objectType) {
        try {
            byte[] bytes = BodyUtils.readBody(in, httpMessage.headers());
            String content = new String(bytes, charset);
            return new DefaultMultiValueEntity(httpMessage, HttpUtils.parseForm(content));
        } catch (IOException e) {
            throw new EntityReadException(
                    "Failed to deserialize entity. [mimeType='application/x-www-form-urlencoded']", e);
        }
    }
}
