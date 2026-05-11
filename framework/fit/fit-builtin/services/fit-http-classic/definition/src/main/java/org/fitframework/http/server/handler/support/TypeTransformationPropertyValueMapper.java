// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.server.HttpClassicServerRequest;
import org.fitframework.http.server.HttpClassicServerResponse;
import org.fitframework.http.server.handler.PropertyValueMapper;
import org.fitframework.serialization.ObjectSerializer;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 表示类型转换的 {@link PropertyValueMapper}。
 * <p>{@link TypeTransformationPropertyValueMapper} 会将获取到的数据转换成指定类型的数据。</p>
 *
 * @author 季聿阶
 * @since 2022-08-31
 */
public class TypeTransformationPropertyValueMapper implements PropertyValueMapper {
    private final PropertyValueMapper mapper;
    private final Type type;

    /**
     * 通过另一个 Http 参数映射器、目标类型、数据是否必须的标记和数据默认值来实例化 {@link
     * TypeTransformationPropertyValueMapper}。
     *
     * @param mapper 表示另一个 Http 参数映射器的 {@link PropertyValueMapper}。
     * @param type 表示目标数据类型的 {@link Type}。
     * @throws IllegalArgumentException 当 {@code mapper} 为 {@code null} 时。
     * @throws IllegalArgumentException 当 {@code type} 为 {@code null} 时。
     */
    public TypeTransformationPropertyValueMapper(PropertyValueMapper mapper, Type type) {
        this.mapper = notNull(mapper, "The http mapper cannot be null.");
        this.type = notNull(type, "The target type cannot be null.");
    }

    @Override
    public Object map(HttpClassicServerRequest request, HttpClassicServerResponse response,
            Map<String, Object> context) {
        Object source = this.mapper.map(request, response, context);
        if (source instanceof String && this.type == String.class) {
            return source;
        }
        if (source != null) {
            ObjectSerializer serializer =
                    request.jsonSerializer().orElseThrow(() -> new IllegalStateException("No json serializer."));
            byte[] serialized = serializer.serialize(source, StandardCharsets.UTF_8);
            source = serializer.deserialize(serialized, StandardCharsets.UTF_8, this.type);
        }
        return source;
    }
}
