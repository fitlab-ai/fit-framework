// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.fitframework.http.client.support.AbstractHttpClassicClient;
import org.fitframework.http.client.support.DefaultHttpClassicClientResponse;
import org.fitframework.http.protocol.ClientResponse;
import org.fitframework.http.protocol.support.DefaultClientResponse;
import org.fitframework.model.MultiValueMap;
import org.fitframework.model.support.DefaultMultiValueMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 为 {@link HttpServerErrorException} 提供单元测试。
 *
 * @author 杭潇
 * @since 2023-02-16
 */
@DisplayName("测试 HttpServerErrorException 类")
public class HttpServerErrorExceptionTest {
    private HttpClassicClientRequest request;
    private HttpClassicClientResponse<?> response;
    private int statusCode;
    private String reasonPhrase;

    @BeforeEach
    void setup() throws IOException {
        this.request = mock(HttpClassicClientRequest.class);
        AbstractHttpClassicClient mock = mock(AbstractHttpClassicClient.class);
        this.statusCode = 200;
        this.reasonPhrase = "testHttpServerErrorException";
        MultiValueMap<String, String> headers = new DefaultMultiValueMap<>();
        headers.add("testKey", "testValue");
        try (InputStream responseStream = new ByteArrayInputStream("TestOfHttpServerErrorException".getBytes(
                StandardCharsets.UTF_8))) {
            ClientResponse clientResponse =
                    new DefaultClientResponse(this.statusCode, this.reasonPhrase, headers, responseStream);
            Class<?> responseType = String.class;
            this.response = new DefaultHttpClassicClientResponse<>(mock, clientResponse, responseType);
        }
    }

    @Test
    @DisplayName("给定一个有效的 http 经典客户端响应，初始化对象成功")
    void givenValidHttpClassicClientResponseThenInitializedSuccessfully() {
        HttpServerErrorException httpServerErrorException = new HttpServerErrorException(this.request, this.response);
        assertThat(httpServerErrorException.statusCode()).isEqualTo(this.statusCode);
        assertThat(httpServerErrorException.getMessage()).isEqualTo(this.getExpectedReason());
    }

    @Test
    @DisplayName("给定有效的 http 经典客户端响应与 Throwable，初始化对象成功")
    void givenValidHttpClassicClientResponseAndThrowableThenInitializedSuccessfully() {
        Throwable throwable = new Throwable("throwSomeThing");
        HttpServerErrorException httpServerErrorException =
                new HttpServerErrorException(this.request, this.response, throwable);
        assertThat(httpServerErrorException.statusCode()).isEqualTo(this.statusCode);
        assertThat(httpServerErrorException.getMessage()).isEqualTo(this.getExpectedReason());
    }

    private String getExpectedReason() {
        return this.statusCode + "(" + this.reasonPhrase + ")";
    }
}
