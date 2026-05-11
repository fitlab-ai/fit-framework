// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.json.schema.support;

import static org.fitframework.util.ObjectUtils.cast;

import org.fitframework.json.schema.util.SchemaTypeUtils;
import org.fitframework.util.MapBuilder;
import org.fitframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 表示 {@link org.fitframework.json.schema.JsonSchema} 的字符串实现。
 *
 * @author 季聿阶
 * @since 2024-03-31
 */
public class StringSchema extends AbstractJsonSchema {
    /**
     * 使用指定的类型初始化 {@link StringSchema} 的新实例。
     *
     * @param type 表示字符串值类型的 {@link Type}。
     */
    public StringSchema(Type type) {
        super(type);
    }

    @Override
    public Map<String, Object> toJsonObject() {
        MapBuilder<String, Object> builder = MapBuilder.<String, Object>get().put("type", "string");
        if (StringUtils.isNotBlank(this.description())) {
            builder.put("description", this.description());
        }
        if (SchemaTypeUtils.isEnumType(this.type())) {
            Class<Enum<?>> enumClass = cast(this.type());
            builder.put("enum", Stream.of(enumClass.getEnumConstants()).map(Enum::name).collect(Collectors.toList()));
        }
        return builder.build();
    }
}
