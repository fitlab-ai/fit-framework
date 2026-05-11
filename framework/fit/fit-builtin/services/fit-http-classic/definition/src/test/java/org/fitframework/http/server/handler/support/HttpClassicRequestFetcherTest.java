// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.fitframework.http.server.HttpClassicServerRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 表示 {@link HttpClassicRequestFetcher} 的单元测试。
 *
 * @author 白鹏坤
 * @since 2023-02-15
 */
@DisplayName("测试 HttpClassicRequestFetcher 类")
class HttpClassicRequestFetcherTest {
    private final HttpClassicRequestFetcher requestFetcher = new HttpClassicRequestFetcher();

    @Test
    @DisplayName("从 Http 请求中获取数据")
    void shouldReturnHttpClassicServerRequest() {
        final HttpClassicServerRequest request = mock(HttpClassicServerRequest.class);
        final Object obj = this.requestFetcher.get(request, null);
        assertThat(obj).isEqualTo(request);
    }
}
