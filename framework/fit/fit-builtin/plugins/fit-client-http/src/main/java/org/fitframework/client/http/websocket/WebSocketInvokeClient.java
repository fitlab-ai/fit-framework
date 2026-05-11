// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.client.http.websocket;

import org.fitframework.client.Request;
import org.fitframework.client.Response;
import org.fitframework.client.http.InvokeClient;
import org.fitframework.client.http.support.AbstractInvokeClient;
import org.fitframework.client.http.support.ConnectionBuilder;
import org.fitframework.client.http.support.ConnectionBuilderFactory;
import org.fitframework.http.client.HttpClassicClient;
import org.fitframework.http.protocol.Protocol;
import org.fitframework.http.websocket.Session;
import org.fitframework.broker.CommunicationType;
import org.fitframework.conf.runtime.ClientConfig;
import org.fitframework.conf.runtime.WorkerConfig;
import org.fitframework.exception.ClientException;
import org.fitframework.inspection.Nonnull;
import org.fitframework.ioc.BeanContainer;

/**
 * 表示 {@link InvokeClient} 的 WebSocket 流式实现。
 *
 * @author 季聿阶
 * @since 2024-05-06
 */
public class WebSocketInvokeClient extends AbstractInvokeClient {
    public WebSocketInvokeClient(BeanContainer container, WorkerConfig workerConfig, ClientConfig clientConfig) {
        super(container, workerConfig, clientConfig);
    }

    @Override
    public Response requestResponse(@Nonnull Request request) {
        HttpClassicClient client = this.buildHttpClient(request);
        ConnectionBuilder builder = ConnectionBuilderFactory.getConnectionBuilder(Protocol.from(request.protocol()));
        String url = builder.buildUrl(request);
        WebsocketInvoker invoker = new WebsocketInvoker(this.getContainer(), request);
        Session session = client.createWebSocketSession(url, invoker);
        invoker.request(session, request);
        try {
            return invoker.waitAndgetResponse();
        } catch (InterruptedException cause) {
            throw new ClientException("Failed to wait websocket request.", cause);
        }
    }

    @Override
    public CommunicationType support() {
        return CommunicationType.SYNC;
    }
}
