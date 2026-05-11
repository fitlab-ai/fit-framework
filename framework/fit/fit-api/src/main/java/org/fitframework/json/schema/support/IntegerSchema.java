// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.json.schema.support;

import org.fitframework.util.MapBuilder;
import org.fitframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * 表示 {@link org.fitframework.json.schema.JsonSchema} 的整数值实现。
 *
 * @author 季聿阶
 * @since 2024-03-31
 */
public class IntegerSchema extends AbstractJsonSchema {
    /**
     * 使用指定的类型初始化 {@link IntegerSchema} 的新实例。
     *
     * @param type 表示整数值类型的 {@link Type}。
     */
    public IntegerSchema(Type type) {
        super(type);
    }

    @Override
    public Map<String, Object> toJsonObject() {
        MapBuilder<String, Object> builder = MapBuilder.<String, Object>get().put("type", "integer");
        if (StringUtils.isNotBlank(this.description())) {
            builder.put("description", this.description());
        }
        return builder.build();
    }
}
