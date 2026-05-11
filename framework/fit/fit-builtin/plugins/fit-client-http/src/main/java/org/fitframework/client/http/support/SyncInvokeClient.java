// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.client.http.support;

import org.fitframework.client.Request;
import org.fitframework.client.Response;
import org.fitframework.client.http.InvokeClient;
import org.fitframework.client.http.util.HttpClientUtils;
import org.fitframework.http.client.HttpClassicClient;
import org.fitframework.http.client.HttpClassicClientRequest;
import org.fitframework.http.client.HttpClassicClientResponse;
import org.fitframework.broker.CommunicationType;
import org.fitframework.conf.runtime.ClientConfig;
import org.fitframework.conf.runtime.WorkerConfig;
import org.fitframework.exception.ClientException;
import org.fitframework.inspection.Nonnull;
import org.fitframework.ioc.BeanContainer;

import java.io.IOException;

/**
 * 表示 {@link InvokeClient} 的同步实现。
 *
 * @author 季聿阶
 * @since 2024-02-17
 */
public class SyncInvokeClient extends AbstractInvokeClient {
    public SyncInvokeClient(BeanContainer container, WorkerConfig workerConfig, ClientConfig clientConfig) {
        super(container, workerConfig, clientConfig);
    }

    @Override
    public Response requestResponse(@Nonnull Request request) {
        HttpClassicClient client = this.buildHttpClient(request);
        HttpClassicClientRequest clientRequest = this.buildClientRequest(client, request);
        clientRequest.entity(this.buildHttpEntity(clientRequest, request));
        try (HttpClassicClientResponse<Object> clientResponse = client.exchange(clientRequest, request.returnType())) {
            return HttpClientUtils.getResponse(this.getContainer(), request, clientResponse);
        } catch (IOException e) {
            throw new ClientException("Failed to close http classic client.", e);
        }
    }

    @Override
    public CommunicationType support() {
        return CommunicationType.SYNC;
    }
}
