// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.openapi3.swagger.entity.support;

import org.fitframework.http.openapi3.swagger.entity.Schema;
import org.fitframework.util.CollectionUtils;
import org.fitframework.util.MapBuilder;
import org.fitframework.util.StringUtils;
import org.fitframework.util.TypeUtils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 表示 {@link Schema} 的枚举实现。
 *
 * @author 季聿阶
 * @since 2023-08-25
 */
public class EnumSchema extends AbstractSchema {
    private final String defaultValue;
    private final Set<String> values;

    public EnumSchema(String name, Type type, String description, List<String> examples) {
        super(name, type, description, examples);
        this.defaultValue = null;
        Class<?> actual = TypeUtils.toClass(type);
        Object[] enumConstants = actual.getEnumConstants();
        this.values = Stream.of(enumConstants).map(Object::toString).collect(Collectors.toSet());
    }

    @Override
    public Map<String, Object> toJson() {
        MapBuilder<String, Object> builder = MapBuilder.<String, Object>get().put("type", "string");
        if (this.defaultValue != null) {
            builder.put("default", this.defaultValue);
        }
        if (this.values != null) {
            builder.put("enum", this.values);
        }
        if (StringUtils.isNotBlank(this.description())) {
            builder.put("description", this.description());
        }
        if (CollectionUtils.isNotEmpty(this.examples())) {
            builder.put("examples", this.examples());
        }
        return builder.build();
    }
}
