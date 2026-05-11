// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.support;

import static org.fitframework.http.server.handler.MockHttpClassicServerRequest.URI_KEY;
import static org.fitframework.http.server.handler.MockHttpClassicServerRequest.URI_VALUE;
import static org.assertj.core.api.Assertions.assertThat;

import org.fitframework.http.server.handler.MockHttpClassicServerRequest;
import org.fitframework.http.server.handler.PropertyValueMapper;
import org.fitframework.http.server.support.DefaultHttpClassicServerRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * 表示 {@link PriorityPropertyValueMapper} 的单元测试。
 *
 * @author 白鹏坤
 * @since 2023-02-21
 */
@DisplayName("测试 PriorityParameterMapper 类")
class PriorityPropertyValueMapperTest {
    @Test
    @DisplayName("选取第一个成功获取的结果")
    void shouldReturnFirstResult() {
        final QueryFetcher queryFetcher1 = new QueryFetcher(ParamValue.custom().name(URI_KEY).build());
        final QueryFetcher queryFetcher2 = new QueryFetcher(ParamValue.custom().name("notExistKey").build());
        UniqueSourcePropertyValueMapper mapper1 = new UniqueSourcePropertyValueMapper(queryFetcher1, false);
        UniqueSourcePropertyValueMapper mapper2 = new UniqueSourcePropertyValueMapper(queryFetcher2, false);
        final List<PropertyValueMapper> mappers = Arrays.asList(mapper1, mapper2);
        final PropertyValueMapper propertyValueMapper = new PriorityPropertyValueMapper(mappers);
        final DefaultHttpClassicServerRequest request = new MockHttpClassicServerRequest().getRequest();
        final Object value = propertyValueMapper.map(request, null, null);
        assertThat(value).isEqualTo(URI_VALUE);
    }
}
