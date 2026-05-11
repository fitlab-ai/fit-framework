// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.serialization;

import static org.assertj.core.api.Assertions.assertThat;

import org.fitframework.serialization.http.websocket.FailMessageContentUtils;
import org.fitframework.serialization.TagLengthValues;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link FailMessageContentUtils} 的基本测试用例。
 *
 * @author 何天放
 * @since 2024-04-16
 */
@DisplayName("测试 FailMessageContentUtils")
public class FailMessageContentUtilsTest {
    @Test
    @DisplayName("能够向 TagLengthValues 中正确设置错误码并获取到所设定值")
    void shouldReturnCorrectCodeFromTagLengthValues() {
        TagLengthValues tlvs = TagLengthValues.create();
        FailMessageContentUtils.setCode(tlvs, 1);
        assertThat(FailMessageContentUtils.getCode(tlvs)).isEqualTo(1);
    }

    @Test
    @DisplayName("能够向 TagLengthValues 中正确设置错误消息并获取到所设定值")
    void shouldReturnCorrectMessageFromTagLengthValues() {
        TagLengthValues tlvs = TagLengthValues.create();
        FailMessageContentUtils.setMessage(tlvs, "test message");
        assertThat(FailMessageContentUtils.getMessage(tlvs)).isEqualTo("test message");
    }

    @Test
    @DisplayName("测试当前工具中的标识是否和需复用的工具中的标识产生冲突")
    void shouldNotThrowException() {
        Assertions.assertThatNoException().isThrownBy(FailMessageContentUtils::new);
    }
}
