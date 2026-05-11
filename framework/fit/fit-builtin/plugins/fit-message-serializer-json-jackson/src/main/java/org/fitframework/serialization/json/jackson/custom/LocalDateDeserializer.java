// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.serialization.json.jackson.custom;

import static org.fitframework.util.StringUtils.blankIf;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import org.fitframework.serialization.json.jackson.JacksonObjectSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 表示 {@link LocalDate} 类型的自定义反序列化器。
 *
 * @author 季聿阶
 * @since 2024-02-21
 */
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
    private final String dateFormat;

    public LocalDateDeserializer(String dateFormat) {
        this.dateFormat = blankIf(dateFormat, JacksonObjectSerializer.DEFAULT_DATE_FORMAT);
    }

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return LocalDate.parse(jsonParser.getText(), DateTimeFormatter.ofPattern(this.dateFormat));
    }
}
