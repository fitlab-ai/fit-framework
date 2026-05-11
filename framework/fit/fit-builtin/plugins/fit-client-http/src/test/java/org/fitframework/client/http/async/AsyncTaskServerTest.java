// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.client.http.async;

import static org.fitframework.http.header.HttpHeaderKey.FIT_CODE;
import static org.fitframework.http.header.HttpHeaderKey.FIT_TLV;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.fitframework.client.Address;
import org.fitframework.client.Request;
import org.fitframework.client.RequestContext;
import org.fitframework.http.client.HttpClassicClient;
import org.fitframework.http.client.HttpClassicClientRequest;
import org.fitframework.http.client.HttpClassicClientResponse;
import org.fitframework.http.protocol.ConfigurableMessageHeaders;
import org.fitframework.http.protocol.HttpRequestMethod;
import org.fitframework.http.protocol.Protocol;
import org.fitframework.serialization.http.HttpUtils;
import org.fitframework.broker.CommunicationType;
import org.fitframework.conf.runtime.SerializationFormat;
import org.fitframework.conf.runtime.WorkerConfig;
import org.fitframework.ioc.BeanContainer;
import org.fitframework.serialization.RequestMetadata;
import org.fitframework.serialization.TagLengthValues;
import org.fitframework.util.ObjectUtils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * 为 {@link AsyncTaskServer} 提供单元测试。
 *
 * @author 季聿阶
 * @since 2024-08-26
 */
@DisplayName("测试 AsyncTaskServer")
public class AsyncTaskServerTest {
    @Test
    @DisplayName("当长轮训时，获取正确的结果")
    void shouldGetCorrectResultWhenAsyncLongPolling() {
        BeanContainer container = mock(BeanContainer.class);
        WorkerConfig config = mock(WorkerConfig.class);
        when(config.id()).thenReturn("workerId");
        when(config.instanceId()).thenReturn("instanceId");
        HttpClassicClient client = mock(HttpClassicClient.class);
        HttpClassicClientRequest request = mock(HttpClassicClientRequest.class);
        when(client.createRequest(eq(HttpRequestMethod.GET), anyString())).thenReturn(request);
        HttpClassicClientResponse<Object> response = ObjectUtils.cast(mock(HttpClassicClientResponse.class));
        when(client.exchange(request)).thenReturn(response);
        ConfigurableMessageHeaders requestHeaders = ConfigurableMessageHeaders.create();
        when(request.headers()).thenReturn(requestHeaders);
        TagLengthValues responseTlv = TagLengthValues.create();
        String taskId = "taskId";
        HttpUtils.setAsyncTaskId(responseTlv, taskId);
        ConfigurableMessageHeaders responseHeaders = ConfigurableMessageHeaders.create()
                .add(FIT_CODE.value(), "200")
                .add(FIT_TLV.value(), HttpUtils.encode(responseTlv.serialize()));
        when(response.headers()).thenReturn(responseHeaders);
        AsyncTaskServer server = new AsyncTaskServer(container,
                config,
                client,
                Request.custom()
                        .protocol(Protocol.HTTP.protocol())
                        .address(Address.create("localhost", 8080))
                        .metadata(RequestMetadata.custom().dataFormat(SerializationFormat.JSON.code()).build())
                        .data(new Object[0])
                        .context(RequestContext.create(1000, TimeUnit.MILLISECONDS, CommunicationType.ASYNC, null))
                        .build(),
                "instanceId");
        AsyncTaskResult actual = server.get(taskId);
        assertThat(actual).isNotNull();
        assertThat(actual.getResponse().metadata().code()).isEqualTo(200);
    }
}
