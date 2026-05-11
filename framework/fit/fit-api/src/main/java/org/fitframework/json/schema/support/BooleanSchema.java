// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.json.schema.support;

import org.fitframework.util.MapBuilder;
import org.fitframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * 表示 {@link org.fitframework.json.schema.JsonSchema} 的布尔值实现。
 *
 * @author 季聿阶
 * @since 2024-03-31
 */
public class BooleanSchema extends AbstractJsonSchema {
    /**
     * 使用指定的类型来初始化 {@link BooleanSchema} 的新实例。
     *
     * @param type 表示布尔值类型的 {@link Type}。
     */
    public BooleanSchema(Type type) {
        super(type);
    }

    @Override
    public Map<String, Object> toJsonObject() {
        MapBuilder<String, Object> builder = MapBuilder.<String, Object>get().put("type", "boolean");
        if (StringUtils.isNotBlank(this.description())) {
            builder.put("description", this.description());
        }
        return builder.build();
    }
}
