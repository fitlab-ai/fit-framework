// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.format.json;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.serialization.ObjectSerializer;
import org.fitframework.util.StringUtils;

import java.lang.reflect.Type;

/**
 * 表示 {@link JsonOutputParser} 的抽象实现。
 *
 * @param <O> 表示输出对象类型。
 * @author 易文渊
 * @since 2024-04-28
 */
public abstract class AbstractJsonOutputParser<O> implements JsonOutputParser<O> {
    private final ObjectSerializer serializer;
    private final Type type;

    /**
     * 创建 {@link AbstractJsonOutputParser} 的实例。
     *
     * @param serializer 表示对象序列化器的 {@link ObjectSerializer}。
     * @param type 表示输出类型 {@link O} 的 {@link Type}。
     * @throws IllegalArgumentException 当 {@code serializer}、{@code schemaManager} 为 {@code null} 时。
     */
    protected AbstractJsonOutputParser(ObjectSerializer serializer, Type type) {
        this.serializer = notNull(serializer, "The serializer cannot be null.");
        this.type = notNull(type, "The type cannot be null.");
    }

    /**
     * 获取输出的 json schema 描述。
     *
     * @return 表示 json schema 描述的 {@link String}。
     */
    protected abstract String jsonSchema();

    @Override
    public O parse(String input) {
        return this.serializer.deserialize(input, this.type);
    }

    @Override
    public String instruction() {
        String template = "The output should be formatted as a JSON instance that conforms to the JSON schema below.\n"
                + "Here is the output schema:\n```\n{0}\n```";
        return StringUtils.format(template, this.jsonSchema());
    }
}