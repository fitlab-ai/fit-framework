// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.okhttp;

import static org.assertj.core.api.Assertions.assertThat;

import org.fitframework.http.client.HttpClassicClientFactory;
import okhttp3.OkHttpClient;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 为 {@link OkHttpClientBuilderFactory} 提供单测。
 *
 * @author 杭潇
 * @since 2024-10-29
 */
@DisplayName("测试 OkHttpClientBuilderFactory")
class OkHttpClientBuilderFactoryTest {
    @DisplayName("设置 ignore-trust 为 true，构建的 OkHttpClient 不为空")
    @Test
    void givenIgnoreTrustTrueThenOkHttpClientIsNotNull() {
        Map<String, Object> config = new HashMap<>();
        config.put("client.http.secure.ignore-trust", true);
        HttpClassicClientFactory.Config build = HttpClassicClientFactory.Config.builder().custom(config).build();
        OkHttpClient.Builder okHttpClientBuilder = OkHttpClientBuilderFactory.getOkHttpClientBuilder(build);
        assertThat(okHttpClientBuilder).isNotNull();
    }
}