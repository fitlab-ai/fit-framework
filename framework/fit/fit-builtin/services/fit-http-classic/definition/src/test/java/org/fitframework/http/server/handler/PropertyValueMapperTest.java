// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 表示 {@link PropertyValueMapper } 的单元测试。
 *
 * @author 白鹏坤
 * @since 2023-02-20
 */
@DisplayName("测试 PropertyValueMapper 类")
class PropertyValueMapperTest {
    @Test
    @DisplayName("获取空的属性值映射器")
    void shouldReturnEmptyParameterMapper() {
        final PropertyValueMapper propertyValueMapper = PropertyValueMapper.empty();
        final MockHttpClassicServerRequest request = new MockHttpClassicServerRequest();
        final Object value = propertyValueMapper.map(request.getRequest(), null, null);
        assertThat(value).isNull();
    }
}
