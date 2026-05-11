// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.serialization;

import static org.assertj.core.api.Assertions.assertThat;

import org.fitframework.util.Convert;
import org.fitframework.util.IoUtils;
import org.fitframework.util.ObjectUtils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * {@link ObjectSerializer} 的单元测试。
 *
 * @author 郭龙飞
 * @since 2023-02-20
 */
@DisplayName("测试 ObjectSerializer 接口")
class ObjectSerializerTest {
    @Test
    @DisplayName("提供 ObjectSerializer 类序列化时，返回正常信息")
    void givenObjectSerializerWhenSerializeThenReturnValue() throws IOException {
        ObjectSerializerImpl serializer = new ObjectSerializerImpl();
        Integer expected = 123;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            serializer.serialize(expected, out);
            try (ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray())) {
                Object actual = serializer.deserialize(in, Integer.class);
                assertThat(actual).isEqualTo(expected);
            }
        }
    }

    @Test
    @DisplayName("提供 ObjectSerializer 类按照编码序列化时，返回正常信息")
    void givenObjectSerializerWhenSerializeByCharsetThenReturnValue() {
        ObjectSerializerImpl serializer = new ObjectSerializerImpl();
        Integer expected = 123;
        byte[] serialize = serializer.serialize(expected, StandardCharsets.UTF_8);
        Object actual = serializer.deserialize(serialize, StandardCharsets.UTF_8, Integer.class);
        assertThat(actual).isEqualTo(expected);
    }

    private static class ObjectSerializerImpl implements ObjectSerializer {
        @Override
        public <T> void serialize(T object, Charset charset, OutputStream out, Map<String, Object> context)
                throws SerializationException {
            Integer intValue = ObjectUtils.cast(object);
            try {
                out.write(intValue);
            } catch (IOException e) {
                throw new SerializationException("Failed to deserialize by String.", e);
            }
        }

        @Override
        public <T> T deserialize(InputStream in, Charset charset, Type objectType, Map<String, Object> context)
                throws SerializationException {
            try {
                byte[] read = IoUtils.read(in);
                return ObjectUtils.cast(Convert.toInteger(read));
            } catch (IOException e) {
                throw new SerializationException("Failed to deserialize by String.", e);
            }
        }
    }
}
