// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.serialization.cbor;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.serialization.MessageSerializer;
import org.fitframework.serialization.util.MessageSerializerUtils;
import org.fitframework.annotation.Component;
import org.fitframework.annotation.Fit;
import org.fitframework.annotation.Order;
import org.fitframework.conf.Config;
import org.fitframework.conf.runtime.SerializationFormat;
import org.fitframework.serialization.ObjectSerializer;
import org.fitframework.util.ArrayUtils;
import org.fitframework.util.ObjectUtils;
import org.fitframework.util.TypeUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 表示 {@link MessageSerializer} 的 CBOR 的实现。
 * <p><a href="https://datatracker.ietf.org/doc/html/rfc8949">RFC 8949</a> 列出了 CBOR 的详细规范。</p>
 *
 * @author 季聿阶
 * @since 2024-01-25
 */
@Order(Order.LOW)
@Component
public class CborMessageSerializer implements MessageSerializer {
    private final ObjectSerializer serializer;
    private final Config config;

    /**
     * 构造一个新的 {@link CborMessageSerializer} 实例。
     *
     * @param serializer 表示用于序列化和反序列化实例的 {@link ObjectSerializer}。
     * @param config 表示配置的 {@link Config}。
     */
    public CborMessageSerializer(@Fit(alias = "cbor") ObjectSerializer serializer, Config config) {
        this.serializer = notNull(serializer, "The CBOR serializer cannot be null.");
        this.config = notNull(config, "The message serializer config cannot be null.");
    }

    @Override
    public byte[] serializeRequest(Type[] argumentTypes, Object[] arguments) {
        return this.serializer.serialize(arguments, UTF_8);
    }

    @Override
    public Object[] deserializeRequest(Type[] argumentTypes, byte[] serialized) {
        MessageSerializerUtils.isSupportedLength(serialized.length, this.config);
        List<Object> deserialized = this.serializer.deserialize(serialized,
                UTF_8,
                TypeUtils.parameterized(List.class, new Type[] {Object.class}));
        Object[] array = new Object[argumentTypes.length];
        for (int i = 0; i < deserialized.size(); i++) {
            array[i] = ObjectUtils.toCustomObject(deserialized.get(i), argumentTypes[i]);
        }
        return array;
    }

    @Override
    public <T> byte[] serializeResponse(Type returnType, T returnData) {
        return this.serializer.serialize(returnData, UTF_8);
    }

    @Override
    public <T> T deserializeResponse(Type returnType, byte[] serialized) {
        if (ArrayUtils.isEmpty(serialized)) {
            return null;
        }
        MessageSerializerUtils.isSupportedLength(serialized.length, this.config);
        return this.serializer.deserialize(serialized, UTF_8, returnType);
    }

    @Override
    public boolean isSupported(Method method) {
        return true;
    }

    @Override
    public int getFormat() {
        return SerializationFormat.CBOR.code();
    }
}
