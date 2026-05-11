// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.serialization.json.jackson.custom;

import static org.fitframework.util.StringUtils.blankIf;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.fitframework.serialization.json.jackson.JacksonObjectSerializer;
import org.fitframework.util.StringUtils;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 表示 {@link ZonedDateTime} 类型的自定义序列化器。
 *
 * @author 季聿阶
 * @since 2024-02-21
 */
public class ZonedDateTimeSerializer extends JsonSerializer<ZonedDateTime> {
    private final String dateFormat;
    private final ZoneId zoneId;

    public ZonedDateTimeSerializer(String dateFormat, String zoneId) {
        this.dateFormat = blankIf(dateFormat, JacksonObjectSerializer.DEFAULT_DATE_TIME_FORMAT);
        this.zoneId = StringUtils.isBlank(zoneId) ? ZoneId.systemDefault() : ZoneId.of(zoneId);
    }

    @Override
    public void serialize(ZonedDateTime zonedDateTime, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {
        ZonedDateTime targetZone = zonedDateTime.withZoneSameInstant(this.zoneId);
        jsonGenerator.writeString(targetZone.toLocalDateTime().format(DateTimeFormatter.ofPattern(this.dateFormat)));
    }
}
