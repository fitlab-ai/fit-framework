// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.proxy.applier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.fitframework.http.client.HttpClassicClient;
import org.fitframework.http.client.HttpClassicClientRequest;
import org.fitframework.http.client.proxy.DestinationSetter;
import org.fitframework.http.client.proxy.RequestBuilder;
import org.fitframework.http.client.proxy.support.DefaultRequestBuilder;
import org.fitframework.http.client.proxy.support.applier.MultiDestinationsPropertyValueApplier;
import org.fitframework.http.client.proxy.support.setter.DestinationSetterInfo;
import org.fitframework.http.protocol.HttpRequestMethod;
import org.fitframework.util.MapBuilder;
import org.fitframework.value.ValueFetcher;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

/**
 * 为 {@link MultiDestinationsPropertyValueApplier} 提供单元测试。
 *
 * @author 王攀博
 * @since 2024-06-13
 */
@DisplayName("测试 MultiDestinationsPropertyValueApplier 接口")
class MultiDestinationsPropertyValueApplierTest {
    private String sourcePath;
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
        this.sourcePath = "k1.k2";
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
    @DisplayName("当提供builder和多级参数时，可以根据jsonPath设置多级参数")
    void shouldSetJsonPathValueWhenApplyGivenBuilderAndValue() {
        // given
        DestinationSetter setter = mock(DestinationSetter.class);
        DestinationSetterInfo setterInfo = new DestinationSetterInfo(setter, this.sourcePath);
        ValueFetcher valueFetcher = mock(ValueFetcher.class);
        RequestBuilder builder = mock(RequestBuilder.class);
        Object object = MapBuilder.<String, Object>get()
                .put("k1", MapBuilder.<String, Object>get().put("k2", this.value).build())
                .build();
        when(valueFetcher.fetch(eq(object), eq(this.sourcePath))).thenReturn(this.value);
        doNothing().when(setter).set(eq(builder), eq(this.value));

        // when
        MultiDestinationsPropertyValueApplier applier =
                new MultiDestinationsPropertyValueApplier(Collections.singletonList(setterInfo), valueFetcher);
        applier.apply(builder, object);

        // then
        verify(valueFetcher, times(1)).fetch(eq(object), eq(this.sourcePath));
        verify(setter, times(1)).set(eq(builder), eq(this.value));
    }
}
