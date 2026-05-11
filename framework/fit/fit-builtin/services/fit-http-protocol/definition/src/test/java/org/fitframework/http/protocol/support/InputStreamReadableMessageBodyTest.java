// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.protocol.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * {@link InputStreamReadableMessageBody} 的单元测试。
 *
 * @author 杭潇
 * @since 2023-02-15
 */
@DisplayName("测试 InputStreamReadableMessageBody 类")
public class InputStreamReadableMessageBodyTest {
    @Test
    @DisplayName("调用读方法，读取结果与预期值相等")
    void invokeReadThenReturnEqualsToTheExpectValue() throws IOException {
        byte[] bytes = "InputStream".getBytes(StandardCharsets.UTF_8);
        try (InputStream in = new ByteArrayInputStream(bytes);
             InputStreamReadableMessageBody inputStreamReadableMessageBody = new InputStreamReadableMessageBody(in)) {
            int actualRead = inputStreamReadableMessageBody.read(bytes, 0, bytes.length);
            assertThat(actualRead).isEqualTo(bytes.length);
        }
    }
}
