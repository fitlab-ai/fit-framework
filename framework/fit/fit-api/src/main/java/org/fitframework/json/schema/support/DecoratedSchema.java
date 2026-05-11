// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.json.schema.support;

import static org.fitframework.inspection.Validation.notBlank;

import org.fitframework.json.schema.JsonSchema;
import org.fitframework.util.StringUtils;

import java.util.Map;

/**
 * 表示 {@link JsonSchema} 的装饰器实现。
 *
 * @author 季聿阶
 * @since 2024-03-31
 */
public class DecoratedSchema extends AbstractJsonSchema {
    private final String name;
    private final String description;
    private final String defaultValue;
    private final JsonSchema schema;

    DecoratedSchema(String name, String description, String defaultValue, JsonSchema schema) {
        super(schema);
        this.name = notBlank(name, "The decorated name cannot be blank.");
        this.description = description;
        this.defaultValue = defaultValue;
        this.schema = schema;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String description() {
        if (StringUtils.isNotBlank(this.description)) {
            return this.description;
        } else {
            return super.description();
        }
    }

    @Override
    public Map<String, Object> toJsonObject() {
        Map<String, Object> map = this.schema.toJsonObject();
        if (StringUtils.isNotBlank(this.description())) {
            map.put("description", this.description());
        }
        if (StringUtils.isNotBlank(this.defaultValue)) {
            map.put("default", this.defaultValue);
        }
        return map;
    }
}
