// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.entity.serializer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.fitframework.http.HttpMessage;
import org.fitframework.http.entity.ObjectEntity;
import org.fitframework.http.entity.support.DefaultObjectEntity;
import org.fitframework.http.protocol.ConfigurableMessageHeaders;
import org.fitframework.serialization.ObjectSerializer;
import org.fitframework.util.ObjectUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 为 {@link JsonEntitySerializer} 提供单元测试。
 *
 * @author 杭潇
 * @since 2023-02-21
 */
@DisplayName("测试 JsonEntitySerializer 类")
public class JsonEntitySerializerTest {
    @SuppressWarnings("rawtypes")
    private JsonEntitySerializer jsonEntitySerializer;

    private final HttpMessage httpMessage = mock(HttpMessage.class);
    @SuppressWarnings("rawtypes")
    private ObjectEntity entity;
    private final Charset charset = StandardCharsets.UTF_8;
    private final byte[] givenByte = "testObject".getBytes(StandardCharsets.UTF_8);

    @SuppressWarnings({"unchecked", "rawtypes"})
    @BeforeEach
    void setup() {
        ObjectSerializer jsonSerializer = new ObjectSerializerImplement();
        this.jsonEntitySerializer = new JsonEntitySerializer(String.class, jsonSerializer);
        this.entity = new DefaultObjectEntity(this.httpMessage, "testObject");
        when(this.httpMessage.headers()).thenReturn(ConfigurableMessageHeaders.create());
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("调用 serializeEntity() 方法，返回值与给定值相等")
    void invokeSerializeEntityMethodThenReturnIsEqualsToTheGivenValue() {
        byte[] actualByte = this.jsonEntitySerializer.serializeEntity(this.entity, this.charset);
        assertThat(actualByte).isEqualTo(this.givenByte);
    }

    @SuppressWarnings("rawtypes")
    @Test
    @DisplayName("调用 deserializeEntity() 方法，返回值与给定值相等")
    void invokeDeserializeEntityMethodThenReturnIsEqualsToTheGivenValue() {
        ObjectEntity objectEntity = ObjectUtils.cast(this.jsonEntitySerializer.deserializeEntity(this.givenByte,
                this.charset,
                this.httpMessage));
        assertThat(objectEntity.object()).isEqualTo(this.entity.object());
    }
}
