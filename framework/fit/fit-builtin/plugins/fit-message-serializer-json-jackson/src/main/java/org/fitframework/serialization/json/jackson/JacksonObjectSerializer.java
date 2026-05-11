// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.serialization.json.jackson;

import static org.fitframework.inspection.Validation.notNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactoryBuilder;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.StreamReadConstraints;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.fitframework.serialization.json.jackson.custom.FitAnnotationIntrospector;
import org.fitframework.serialization.json.jackson.custom.LocalDateDeserializer;
import org.fitframework.serialization.json.jackson.custom.LocalDateSerializer;
import org.fitframework.serialization.json.jackson.custom.LocalDateTimeDeserializer;
import org.fitframework.serialization.json.jackson.custom.LocalDateTimeSerializer;
import org.fitframework.serialization.json.jackson.custom.ZonedDateTimeDeserializer;
import org.fitframework.serialization.json.jackson.custom.ZonedDateTimeSerializer;
import org.fitframework.annotation.Alias;
import org.fitframework.annotation.Component;
import org.fitframework.annotation.Value;
import org.fitframework.serialization.ObjectSerializer;
import org.fitframework.serialization.SerializationException;
import org.fitframework.serialization.annotation.BuiltinSerializer;
import org.fitframework.util.IoUtils;
import org.fitframework.util.ObjectUtils;
import org.fitframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Map;

/**
 * 表示 Json 格式的序列化器。
 *
 * @author 季聿阶
 * @author 易文渊
 * @since 2022-08-03
 */
@Component("json")
@Alias("jackson")
@BuiltinSerializer
public class JacksonObjectSerializer implements ObjectSerializer {
    /** 表示默认的日期时间的格式。 */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /** 表示默认的日期格式。 */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    private final ObjectMapper mapper;
    private final boolean isDebug;

    /**
     * 序列化对象为 Json 格式的字符串。
     *
     * @param dateTimeFormat 表示日期时间格式的 {@link String}。
     * @param dateFormat 表示日期格式的 {@link String}。
     * @param zoneId 表示时区唯一标识的 {@link String}。
     */
    public JacksonObjectSerializer(@Value("${date-time-format}") String dateTimeFormat,
            @Value("${date-format}") String dateFormat, @Value("${time-zone}") String zoneId,
            @Value("${fit.debug:false}") boolean isDebug) {
        VisibilityChecker<VisibilityChecker.Std> visibilityChecker = VisibilityChecker.Std.defaultInstance()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE);
        this.mapper = new ObjectMapper(new JsonFactoryBuilder().streamReadConstraints(StreamReadConstraints.builder()
                .maxStringLength(Integer.MAX_VALUE)
                .build()).build()).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setAnnotationIntrospector(new FitAnnotationIntrospector())
                .setVisibility(visibilityChecker)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormat));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormat));
        module.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer(dateTimeFormat, zoneId));
        module.addDeserializer(ZonedDateTime.class, new ZonedDateTimeDeserializer(dateTimeFormat, zoneId));
        module.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormat));
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormat));
        this.mapper.registerModule(module);
        this.isDebug = isDebug;
    }

    @Override
    public <T> void serialize(T object, Charset charset, OutputStream out, Map<String, Object> context)
            throws SerializationException {
        notNull(out, "The output stream cannot be null.");
        try (JsonGenerator generator = this.mapper.createGenerator(out, this.getJsonEncoding(charset))) {
            this.mapper.writeValue(generator, object);
        } catch (IOException e) {
            throw new SerializationException("Failed to serialize by Jackson.", e);
        }
    }

    private JsonEncoding getJsonEncoding(Charset charset) {
        for (JsonEncoding jsonEncoding : JsonEncoding.values()) {
            if (StringUtils.equalsIgnoreCase(charset.name(), jsonEncoding.getJavaName())) {
                return jsonEncoding;
            }
        }
        return JsonEncoding.UTF8;
    }

    @Override
    public <T> T deserialize(InputStream in, Charset charset, Type objectType, Map<String, Object> context)
            throws SerializationException {
        notNull(in, "The input stream cannot be null.");
        Type actualType = ObjectUtils.nullIf(objectType, Object.class);
        Map<String, Object> actualContext = ObjectUtils.getIfNull(context, Collections::emptyMap);
        String read = null;
        try {
            if (actualContext.containsKey("length")) {
                read = IoUtils.content(in, ObjectUtils.<Integer>cast(actualContext.get("length")));
            } else {
                read = IoUtils.content(in);
            }
            return this.mapper.readValue(read, this.mapper.constructType(actualType));
        } catch (IOException e) {
            String message = "Failed to deserialize by Jackson.";
            if (this.isDebug) {
                message += " [content=" + read + "]";
            }
            throw new SerializationException(message, e);
        }
    }

    /**
     * 获取 Jackson 的核心序列化器。
     * <p>因为需要保证 Jackson 的序列化配置全局统一，因此需要将配置好的序列化器透出，让其他 Jackson 序列化器使用。</p>
     *
     * @return 表示 Jackson 的核心序列化器的 {@link ObjectMapper}。
     */
    public ObjectMapper mapper() {
        return this.mapper;
    }
}
