// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.serialization.json.jackson.custom;

import static org.fitframework.util.StringUtils.blankIf;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.fitframework.serialization.json.jackson.JacksonObjectSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 表示 {@link LocalDateTime} 类型的自定义序列化器。
 *
 * @author 季聿阶
 * @since 2024-02-21
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    private final String dateFormat;

    public LocalDateTimeSerializer(String dateFormat) {
        this.dateFormat = blankIf(dateFormat, JacksonObjectSerializer.DEFAULT_DATE_TIME_FORMAT);
    }

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(localDateTime.format(DateTimeFormatter.ofPattern(this.dateFormat)));
    }
}
