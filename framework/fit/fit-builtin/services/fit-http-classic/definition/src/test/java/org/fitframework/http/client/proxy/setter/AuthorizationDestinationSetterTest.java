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

import org.fitframework.http.client.HttpClassicClient;
import org.fitframework.http.client.HttpClassicClientRequest;
import org.fitframework.http.client.proxy.Authorization;
import org.fitframework.http.client.proxy.DestinationSetter;
import org.fitframework.http.client.proxy.RequestBuilder;
import org.fitframework.http.client.proxy.support.DefaultRequestBuilder;
import org.fitframework.http.client.proxy.support.setter.AuthorizationDestinationSetter;
import org.fitframework.http.protocol.ConfigurableMessageHeaders;
import org.fitframework.http.protocol.HttpRequestMethod;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 添加 {@link AuthorizationDestinationSetter} 的测试用例。
 *
 * @author 王攀博
 * @since 2024-11-28
 */
@DisplayName("测试 AuthorizationDestinationSetter")
public class AuthorizationDestinationSetterTest {
    private static final String AUTH_HEADER_KEY = "Authorization";

    private RequestBuilder requestBuilder;
    private HttpClassicClientRequest expectRequest;

    @BeforeEach
    void setup() {
        String protocol = "http";
        String domain = "test_domain";
        String pathPattern = "/gid/fit";
        HttpRequestMethod method = HttpRequestMethod.POST;
        HttpClassicClient client = mock(HttpClassicClient.class);
        this.expectRequest = mock(HttpClassicClientRequest.class);
        this.requestBuilder = new DefaultRequestBuilder().client(client)
                .protocol(protocol)
                .domain(domain)
                .pathPattern(pathPattern)
                .method(method);
        when(client.createRequest(any(), any())).thenReturn(this.expectRequest);
    }

    @AfterEach
    void teardown() {
        this.requestBuilder = null;
    }

    @Test
    @DisplayName("当提供Api Key鉴权的默认数据时的测试")
    void shouldReturnApiWhenSetGivenApiKeyInfo() {
        // given
        ConfigurableMessageHeaders headers = mock(ConfigurableMessageHeaders.class);
        when(this.expectRequest.headers()).thenReturn(headers);
        when(headers.all("ApiKeyAuthKey")).thenReturn(Collections.singletonList("ApiKeyAuthValue"));

        Map<String, Object> authorizationInfo = new HashMap<>();
        authorizationInfo.put("type", "ApiKey");
        authorizationInfo.put("key", "ApiKeyAuthKey");
        authorizationInfo.put("value", "ApiKeyAuthValue");

        // when
        this.requestBuilder.authorization(Authorization.create(authorizationInfo));
        HttpClassicClientRequest request = this.requestBuilder.build();

        // then
        verify(headers, times(1)).set("ApiKeyAuthKey", Collections.singletonList("ApiKeyAuthValue"));
        assertThat(request.headers().all("ApiKeyAuthKey").get(0)).isEqualTo("ApiKeyAuthValue");
    }

    @Test
    @DisplayName("当提供Basic鉴权的默认数据时的测试")
    void shouldReturnBasicWhenSetGivenApiKeyInfo() {
        // given
        String basicAuthValue =
                "Basic " + Base64.getEncoder().encodeToString("xiaoxiao:xiaoxiaopwd".getBytes(StandardCharsets.UTF_8));
        ConfigurableMessageHeaders headers = mock(ConfigurableMessageHeaders.class);
        when(this.expectRequest.headers()).thenReturn(headers);
        when(headers.all("Authorization")).thenReturn(Collections.singletonList(basicAuthValue));

        Map<String, Object> authorizationInfo = new HashMap<>();
        authorizationInfo.put("type", "Basic");
        authorizationInfo.put("username", "xiaoxiao");
        authorizationInfo.put("password", "xiaoxiaopwd");

        // when
        this.requestBuilder.authorization(Authorization.create(authorizationInfo));
        HttpClassicClientRequest request = this.requestBuilder.build();

        // then
        verify(headers, times(1)).set("Authorization", Collections.singletonList(basicAuthValue));
        assertThat(request.headers().all("Authorization").get(0)).isEqualTo(basicAuthValue);
    }

    @Test
    @DisplayName("当提供Bearer鉴权的默认数据时的测试")
    void shouldReturnBearerWhenSetGivenApiKeyInfo() {
        // given
        String token = "bTokenTest";
        String bearerAuthValue = "Bearer " + token;
        ConfigurableMessageHeaders headers = mock(ConfigurableMessageHeaders.class);
        when(this.expectRequest.headers()).thenReturn(headers);
        when(headers.all("Authorization")).thenReturn(Collections.singletonList(bearerAuthValue));

        Map<String, Object> authorizationInfo = new HashMap<>();
        authorizationInfo.put("type", "Bearer");
        authorizationInfo.put("token", token);

        // when
        this.requestBuilder.authorization(Authorization.create(authorizationInfo));
        HttpClassicClientRequest request = this.requestBuilder.build();

        // then
        verify(headers, times(1)).set("Authorization", Collections.singletonList(bearerAuthValue));
        assertThat(request.headers().all("Authorization").get(0)).isEqualTo(bearerAuthValue);
    }

    @Test
    @DisplayName("当向builder中设置鉴权信息")
    void shouldCallOnceWhenSetAuthorizationGivenKeyValue() {
        // given
        String tokenKey = "token";
        String tokenValue = "bTokenTest";
        DestinationSetter setter = new AuthorizationDestinationSetter(tokenKey);
        Map<String, Object> authorizationInfo = new HashMap<>();
        authorizationInfo.put("type", "Bearer");

        ConfigurableMessageHeaders headers = mock(ConfigurableMessageHeaders.class);
        when(this.expectRequest.headers()).thenReturn(headers);
        when(headers.all(AUTH_HEADER_KEY)).thenReturn(Collections.singletonList("Bearer " + "bTokenTest"));

        // when
        this.requestBuilder.authorization(Authorization.create(authorizationInfo));
        setter.set(this.requestBuilder, tokenValue);
        HttpClassicClientRequest request = this.requestBuilder.build();

        // then
        request.headers().all(AUTH_HEADER_KEY).get(0).equals("Bearer bTokenTest");
    }
}