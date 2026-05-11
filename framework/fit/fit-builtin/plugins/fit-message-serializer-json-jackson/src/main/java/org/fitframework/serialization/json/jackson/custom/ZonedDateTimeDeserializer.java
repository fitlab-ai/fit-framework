// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.serialization.json.jackson.custom;

import static org.fitframework.util.StringUtils.blankIf;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import org.fitframework.serialization.json.jackson.JacksonObjectSerializer;
import org.fitframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 表示 {@link ZonedDateTime} 类型的自定义反序列化器。
 *
 * @author 季聿阶
 * @since 2024-02-21
 */
public class ZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {
    private final String dateFormat;
    private final ZoneId zoneId;

    public ZonedDateTimeDeserializer(String dateFormat, String zoneId) {
        this.dateFormat = blankIf(dateFormat, JacksonObjectSerializer.DEFAULT_DATE_TIME_FORMAT);
        this.zoneId = StringUtils.isBlank(zoneId) ? ZoneId.systemDefault() : ZoneId.of(zoneId);
    }

    @Override
    public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        LocalDateTime parsed = LocalDateTime.parse(jsonParser.getText(), DateTimeFormatter.ofPattern(this.dateFormat));
        return ZonedDateTime.of(parsed, this.zoneId);
    }
}
