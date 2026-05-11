// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.entity.serializer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.fitframework.http.HttpMessage;
import org.fitframework.http.entity.EntitySerializer;
import org.fitframework.http.entity.TextEntity;
import org.fitframework.http.entity.support.DefaultTextEntity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 为 {@link TextEntitySerializer} 提供单元测试。
 *
 * @author 杭潇
 * @since 2023-02-21
 */
@DisplayName("测试 TextEntitySerializer 类")
public class TextEntitySerializerTest {
    @Test
    @DisplayName("调用 serializeEntity() 方法，返回值与给定值相等")
    void invokeSerializeEntityMethodThenReturnIsEqualsToTheGivenValue() {
        EntitySerializer<TextEntity> instance = TextEntitySerializer.INSTANCE;
        HttpMessage httpMessage = mock(HttpMessage.class);
        TextEntity entity = new DefaultTextEntity(httpMessage, "testContent");
        Charset charset = StandardCharsets.UTF_8;
        byte[] bytes = instance.serializeEntity(entity, charset);
        assertThat(bytes).isEqualTo("testContent".getBytes(StandardCharsets.UTF_8));
    }
}
