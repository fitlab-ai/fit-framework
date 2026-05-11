// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.support;

import static org.fitframework.http.server.handler.MockHttpClassicServerRequest.HEADER_KEY;
import static org.assertj.core.api.Assertions.assertThat;

import org.fitframework.http.server.handler.MockHttpClassicServerRequest;
import org.fitframework.http.server.handler.PropertyValueMapper;
import org.fitframework.http.server.support.DefaultHttpClassicServerRequest;
import org.fitframework.util.ObjectUtils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 表示 {@link MultiSourcesPropertyValueMapper} 的单元测试。
 *
 * @author 邬涨财
 * @since 2024-02-17
 */
public class MultiSourcePropertyValueMapperTest {
    @Test
    @DisplayName("将 Http 请求和响应通过规则映射成为 Map")
    void givenMapWhenDestinationNameNotEmptyThenReturnMap() {
        final MockHttpClassicServerRequest serverRequest = new MockHttpClassicServerRequest();
        final DefaultHttpClassicServerRequest request = serverRequest.getRequest();
        List<SourceFetcherInfo> sourceFetcherInfos =
                Collections.singletonList(new SourceFetcherInfo(new HeaderFetcher(ParamValue.custom()
                        .name(HEADER_KEY)
                        .build()), "a.b", false));
        PropertyValueMapper mapper = new MultiSourcesPropertyValueMapper(sourceFetcherInfos);
        final Object value = mapper.map(request, null, null);
        Map<String, Map<?, ?>> map = ObjectUtils.cast(value);
        assertThat(map).containsKey("a");
    }
}
