// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.entity.serializer;

import static org.fitframework.util.ObjectUtils.cast;

import org.fitframework.serialization.ObjectSerializer;
import org.fitframework.serialization.SerializationException;
import org.fitframework.util.Convert;
import org.fitframework.util.IoUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 表示 {@link ObjectSerializer} 接口的实现
 *
 * @author 杭潇
 * @since 2023-02-21
 */
public class ObjectSerializerImplement implements ObjectSerializer {
    @Override
    public <T> void serialize(T object, Charset charset, OutputStream out, Map<String, Object> context)
            throws SerializationException {
        String intValue = cast(object);
        try {
            out.write(intValue.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new SerializationException("Failed to deserialize by String.", e);
        }
    }

    @Override
    public <T> T deserialize(InputStream in, Charset charset, Type objectType, Map<String, Object> context)
            throws SerializationException {
        try {
            byte[] read = IoUtils.read(in);
            return cast(Convert.toString(read));
        } catch (IOException e) {
            throw new SerializationException("Failed to deserialize by String.", e);
        }
    }
}
