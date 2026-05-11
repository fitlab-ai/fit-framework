// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.support;

import static org.fitframework.http.server.handler.MockHttpClassicServerRequest.HEADER_KEY;
import static org.fitframework.http.server.handler.MockHttpClassicServerRequest.HEADER_VALUE;
import static org.assertj.core.api.Assertions.assertThat;

import org.fitframework.http.server.handler.MockHttpClassicServerRequest;
import org.fitframework.http.server.support.DefaultHttpClassicServerRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 表示 {@link UniqueSourcePropertyValueMapper } 的单元测试。
 *
 * @author 白鹏坤
 * @since 2023-02-20
 */
@DisplayName("测试 UniqueSourceParameterMapper 类")
class UniqueSourcePropertyValueMapperTest {
    @Test
    @DisplayName("将 Http 请求和响应通过规则映射成为一个指定值")
    void givenMapWhenDestinationNameIsNullThenReturnString() {
        final MockHttpClassicServerRequest serverRequest = new MockHttpClassicServerRequest();
        final DefaultHttpClassicServerRequest request = serverRequest.getRequest();
        UniqueSourcePropertyValueMapper mapper =
                new UniqueSourcePropertyValueMapper(new HeaderFetcher(ParamValue.custom().name(HEADER_KEY).build()),
                        false);
        final Object value = mapper.map(request, null, null);
        assertThat(value).isEqualTo(HEADER_VALUE);
    }
}
