// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.fitframework.http.HttpResource;
import org.fitframework.http.protocol.ConfigurableMessageHeaders;
import org.fitframework.http.protocol.ConfigurableStatusLine;
import org.fitframework.http.protocol.HttpRequestMethod;
import org.fitframework.http.protocol.HttpVersion;
import org.fitframework.http.protocol.MessageHeaders;
import org.fitframework.http.protocol.QueryCollection;
import org.fitframework.http.protocol.RequestLine;
import org.fitframework.http.protocol.ServerRequest;
import org.fitframework.http.protocol.ServerResponse;
import org.fitframework.http.protocol.support.DefaultMessageHeaders;
import org.fitframework.http.protocol.support.DefaultRequestLine;
import org.fitframework.http.protocol.support.DefaultStatusLine;
import org.fitframework.http.server.HttpClassicServerRequest;
import org.fitframework.http.server.HttpClassicServerResponse;
import org.fitframework.http.server.handler.PropertyValueMapper;
import org.fitframework.http.server.support.DefaultHttpClassicServerRequest;
import org.fitframework.http.server.support.DefaultHttpClassicServerResponse;
import org.fitframework.annotation.Scope;
import org.fitframework.exception.MethodInvocationException;
import org.fitframework.util.ReflectionUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 表示 {@link DefaultHttpExceptionHandler} 的单元测试。
 *
 * @author 杭潇
 * @since 2023-02-28
 */
@DisplayName("测试 DefaultHttpExceptionHandler 类")
public class DefaultHttpExceptionHandlerTest {
    private DefaultHttpExceptionHandler defaultHttpExceptionHandler;
    private HttpClassicServerRequest request;
    private HttpClassicServerResponse response;
    private ServerRequest serverRequest;
    private ServerResponse serverResponse;

    @BeforeEach
    void setup() {
        Object target = "testTarget";
        int statusCode = 200;
        List<PropertyValueMapper> propertyValueMappers = new ArrayList<>();
        propertyValueMappers.add(mock(PropertyValueMapper.class));
        propertyValueMappers.add(mock(PropertyValueMapper.class));
        propertyValueMappers.add(mock(PropertyValueMapper.class));
        Method method = ReflectionUtils.getDeclaredMethod(DefaultHttpExceptionHandler.class,
                "handle",
                HttpClassicServerRequest.class,
                HttpClassicServerResponse.class,
                Throwable.class);
        this.defaultHttpExceptionHandler =
                new DefaultHttpExceptionHandler(target, method, statusCode, propertyValueMappers, Scope.PLUGIN);
    }

    @AfterEach
    void teardown() throws IOException {
        this.serverRequest.close();
        this.serverResponse.close();
    }

    private void initializeRequest() {
        HttpResource httpResource = mock(HttpResource.class);
        this.serverRequest = mock(ServerRequest.class);
        RequestLine startLine = new DefaultRequestLine(HttpVersion.HTTP_1_0,
                HttpRequestMethod.CONNECT,
                "testUri",
                QueryCollection.create());
        MessageHeaders headers = new DefaultMessageHeaders();
        when(this.serverRequest.startLine()).thenReturn(startLine);
        when(this.serverRequest.headers()).thenReturn(headers);
        this.request = new DefaultHttpClassicServerRequest(httpResource, this.serverRequest);
    }

    private void initializeResponse() {
        this.serverResponse = mock(ServerResponse.class);
        HttpResource httpResource = mock(HttpResource.class);
        ConfigurableStatusLine configurableStatusLine =
                new DefaultStatusLine(HttpVersion.HTTP_1_0, 200, "testReasonPhrase");
        when(this.serverResponse.startLine()).thenReturn(configurableStatusLine);
        ConfigurableMessageHeaders configurableMessageHeaders = new DefaultMessageHeaders();
        when(this.serverResponse.headers()).thenReturn(configurableMessageHeaders);
        this.response = new DefaultHttpClassicServerResponse(httpResource, this.serverResponse);
    }

    @Test
    @DisplayName("给定调用参数不一致，抛出异常")
    void givenInvokeParametersAreInconsistentThenThrowException() {
        this.initializeRequest();
        this.initializeResponse();
        Throwable cause = new Exception();
        MethodInvocationException methodInvocationException = catchThrowableOfType(MethodInvocationException.class,
                () -> this.defaultHttpExceptionHandler.handle(this.request, this.response, cause));
        assertThat(methodInvocationException).isNotNull();
    }
}
