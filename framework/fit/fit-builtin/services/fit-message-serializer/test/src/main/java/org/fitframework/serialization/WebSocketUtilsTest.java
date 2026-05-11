// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.serialization;

import static org.assertj.core.api.Assertions.assertThat;

import org.fitframework.serialization.http.websocket.WebSocketUtils;
import org.fitframework.serialization.TagLengthValues;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link WebSocketUtils} 的基本测试用例。
 *
 * @author 何天放
 * @since 2024-04-16
 */
@DisplayName("测试 WebSocketUtils")
public class WebSocketUtilsTest {
    @Test
    @DisplayName("能够向 TagLengthValues 中正确设置索引并获取到所设定值")
    void shouldReturnCorrectIndexFromTagLengthValues() {
        TagLengthValues tlvs = TagLengthValues.create();
        WebSocketUtils.setIndex(tlvs, 1);
        assertThat(WebSocketUtils.getIndex(tlvs)).isEqualTo(1);
    }

    @Test
    @DisplayName("能够向 TagLengthValues 中正确设置类型信息并获取到所设定值")
    void shouldReturnCorrectTypeFromTagLengthValues() {
        TagLengthValues tlvs = TagLengthValues.create();
        WebSocketUtils.setType(tlvs, 1);
        assertThat(WebSocketUtils.getType(tlvs)).isEqualTo(1);
    }

    @Test
    @DisplayName("能够向 TagLengthValues 中正确设置内容并获取到所设定值")
    void shouldReturnCorrectContentFromTagLengthValues() {
        TagLengthValues tlvs = TagLengthValues.create();
        byte[] byteArray = {1, 2, 3, 4, 5};
        WebSocketUtils.setContent(tlvs, byteArray);
        assertThat(WebSocketUtils.getContent(tlvs)).isEqualTo(byteArray);
    }
}
