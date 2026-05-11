// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.entity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

import org.fitframework.http.HttpMessage;
import org.fitframework.http.entity.support.DefaultWritableBinaryEntity;
import org.fitframework.http.protocol.ServerResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 表示 {@link WritableBinaryEntity} 的单元测试。
 *
 * @author 白鹏坤
 * @since 2023-02-22
 */
@DisplayName("测试 WritableBinaryEntity 类")
class WritableBinaryEntityTest {
    private final byte[] writeBytes = new byte[] {1, 2};
    private WritableBinaryEntity writableBinaryEntity;
    private ServerResponse serverResponse;

    @BeforeEach
    void setup() {
        this.serverResponse = mock(ServerResponse.class);
        final HttpMessage httpMessage = mock(HttpMessage.class);
        this.writableBinaryEntity = new DefaultWritableBinaryEntity(httpMessage, this.serverResponse);
    }

    @Test
    @DisplayName("向 Http 消息体中写入数据成功")
    void shouldReturnWriteSuccessfully() {
        assertDoesNotThrow(() -> this.writableBinaryEntity.write(this.writeBytes));
        assertDoesNotThrow(() -> this.writableBinaryEntity.flush());
    }
}
