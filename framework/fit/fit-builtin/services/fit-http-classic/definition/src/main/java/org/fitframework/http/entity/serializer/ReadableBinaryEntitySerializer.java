// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.entity.serializer;

import org.fitframework.http.HttpMessage;
import org.fitframework.http.entity.EntitySerializer;
import org.fitframework.http.entity.EntityWriteException;
import org.fitframework.http.entity.ReadableBinaryEntity;
import org.fitframework.http.entity.support.DefaultReadableBinaryEntity;
import org.fitframework.inspection.Nonnull;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * 表示可以任意消息体格式的序列化器。
 *
 * @author 季聿阶
 * @since 2023-10-09
 */
public class ReadableBinaryEntitySerializer implements EntitySerializer<ReadableBinaryEntity> {
    /** 表示 {@link ReadableBinaryEntitySerializer} 的单例实现。 */
    public static final EntitySerializer<ReadableBinaryEntity> INSTANCE = new ReadableBinaryEntitySerializer();

    private ReadableBinaryEntitySerializer() {}

    @Override
    public void serializeEntity(@Nonnull ReadableBinaryEntity entity, Charset charset, OutputStream out) {
        throw new EntityWriteException("Unsupported to serialize entity of Content-Type '*/*'.");
    }

    @Override
    public ReadableBinaryEntity deserializeEntity(@Nonnull InputStream in, Charset charset,
            @Nonnull HttpMessage httpMessage, Type objectType) {
        return new DefaultReadableBinaryEntity(httpMessage, in);
    }
}
