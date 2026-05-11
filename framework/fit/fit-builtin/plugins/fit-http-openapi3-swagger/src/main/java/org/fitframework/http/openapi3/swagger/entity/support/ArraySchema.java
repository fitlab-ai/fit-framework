// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.openapi3.swagger.entity.support;

import static org.fitframework.util.ObjectUtils.cast;

import org.fitframework.http.openapi3.swagger.entity.Schema;
import org.fitframework.util.MapBuilder;
import org.fitframework.util.StringUtils;
import org.fitframework.util.TypeUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 表示 {@link Schema} 的数组实现。
 *
 * @author 季聿阶
 * @since 2023-08-25
 */
public class ArraySchema extends AbstractSchema {
    private final Schema items;

    public ArraySchema(String name, Type type, String description, List<String> examples) {
        super(name, type, description, examples);
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = cast(type);
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            this.items = Schema.create(actualTypeArguments[0], description, examples);
        } else {
            Class<?> clazz = TypeUtils.toClass(type);
            this.items = Schema.create(clazz.getComponentType(), description, examples);
        }
    }

    @Override
    public Map<String, Object> toJson() {
        MapBuilder<String, Object> builder = MapBuilder.<String, Object>get().put("type", "array");
        if (this.items != null) {
            builder.put("items", this.items.toJson());
        }
        if (StringUtils.isNotBlank(this.description())) {
            builder.put("description", this.description());
        }
        return builder.build();
    }
}
