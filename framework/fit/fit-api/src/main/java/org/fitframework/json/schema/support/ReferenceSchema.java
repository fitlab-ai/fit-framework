// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.json.schema.support;

import static org.fitframework.util.ObjectUtils.nullIf;

import org.fitframework.json.schema.JsonSchema;
import org.fitframework.util.MapBuilder;
import org.fitframework.util.StringUtils;

import java.util.Map;

/**
 * 表示 {@link JsonSchema} 的引用实现。
 *
 * @author 季聿阶
 * @since 2024-03-31
 */
public class ReferenceSchema extends AbstractJsonSchema {
    private final String reference;
    private final JsonSchema schema;

    ReferenceSchema(String reference, JsonSchema schema) {
        super(schema);
        this.reference = nullIf(reference, StringUtils.EMPTY);
        this.schema = schema;
    }

    @Override
    public Map<String, Object> toJsonObject() {
        return MapBuilder.<String, Object>get()
                .put("type", "object")
                .put("#ref", this.reference + this.schema.name())
                .build();
    }
}
