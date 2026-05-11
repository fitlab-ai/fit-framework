// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.proxy.setter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.fitframework.http.Cookie;
import org.fitframework.http.client.HttpClassicClient;
import org.fitframework.http.client.HttpClassicClientRequest;
import org.fitframework.http.client.proxy.DestinationSetter;
import org.fitframework.http.client.proxy.RequestBuilder;
import org.fitframework.http.client.proxy.support.DefaultRequestBuilder;
import org.fitframework.http.client.proxy.support.setter.CookieDestinationSetter;
import org.fitframework.http.header.ConfigurableCookieCollection;
import org.fitframework.http.protocol.HttpRequestMethod;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * 为 {@link DestinationSetter} 提供单元测试。
 *
 * @author 王攀博
 * @since 2024-06-12
 */
@DisplayName("测试 DestinationSetter 接口")
class CookieDestinationSetterTest {
    private String key;
    private String value;
    private RequestBuilder requestBuilder;
    private HttpClassicClientRequest expectRequest;
    private String protocol;
    private String domain;
    private HttpRequestMethod method;
    private String pathPattern;
    private HttpClassicClient client;

    @BeforeEach
    void setup() {
        this.key = "test_key";
        this.value = "test_value";
        this.protocol = "http";
        this.domain = "test_domain";
        this.pathPattern = "/fit/{gid}";
        this.method = HttpRequestMethod.POST;
        this.client = mock(HttpClassicClient.class);
        this.expectRequest = mock(HttpClassicClientRequest.class);
        this.requestBuilder = new DefaultRequestBuilder().client(this.client)
                .protocol(this.protocol)
                .domain(this.domain)
                .pathPattern(this.pathPattern)
                .method(this.method);
        when(this.client.createRequest(any(), any())).thenReturn(this.expectRequest);
    }

    @AfterEach
    void teardown() {
        this.requestBuilder = null;
    }

    @Test
    @DisplayName("当提供key时，返回cookie中的value")
    void shouldReturnCookieValueWhenGetValueFromCookieGivenKey() {
        // given
        ConfigurableCookieCollection cookieCollection = mock(ConfigurableCookieCollection.class);
        when(this.expectRequest.cookies()).thenReturn(cookieCollection);
        when(cookieCollection.get(any())).thenReturn(Optional.of(Cookie.builder().value(this.value).build()));

        // when
        DestinationSetter cookieDestinationSetter = new CookieDestinationSetter(this.key);
        cookieDestinationSetter.set(requestBuilder, this.value);
        HttpClassicClientRequest request = requestBuilder.build();

        // then
        assertThat(request.cookies().get(this.key).get().value()).isEqualTo(this.value);
        verify(cookieCollection, times(1)).add(any(Cookie.class));
        verify(cookieCollection, times(1)).get(any());
    }
}
