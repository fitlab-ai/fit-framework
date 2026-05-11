// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.tool.mcp.client.support;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.client.transport.HttpClientStreamableHttpTransport;
import io.modelcontextprotocol.json.jackson.JacksonMcpJsonMapper;
import org.fitframework.fel.tool.mcp.client.McpClient;
import org.fitframework.fel.tool.mcp.client.McpClientFactory;
import org.fitframework.fel.tool.mcp.client.elicitation.ElicitRequest;
import org.fitframework.fel.tool.mcp.client.elicitation.ElicitResult;
import org.fitframework.annotation.Component;
import org.fitframework.annotation.Value;
import org.fitframework.inspection.Nullable;

import java.util.function.Function;

/**
 * Represents a factory for creating instances of the {@link DefaultMcpClient}.
 * This class is responsible for initializing and configuring.
 *
 * @author 季聿阶
 * @since 2025-05-21
 */
@Component
public class DefaultMcpClientFactory implements McpClientFactory {
    private final int requestTimeoutSeconds;

    /**
     * Constructs a new instance of the DefaultMcpClientFactory.
     *
     * @param requestTimeoutSeconds The timeout duration of requests. Units: seconds.
     */
    public DefaultMcpClientFactory(@Value("${mcp.client.request.timeout-seconds}") int requestTimeoutSeconds) {
        this.requestTimeoutSeconds = requestTimeoutSeconds > 0 ? requestTimeoutSeconds : 180;
    }

    @Override
    public McpClient createStreamable(String baseUri, String sseEndpoint,
            @Nullable Function<ElicitRequest, ElicitResult> elicitationHandler) {
        HttpClientStreamableHttpTransport transport = HttpClientStreamableHttpTransport.builder(baseUri)
                .jsonMapper(new JacksonMcpJsonMapper(new ObjectMapper()))
                .endpoint(sseEndpoint)
                .build();
        return new DefaultMcpClient(baseUri, sseEndpoint, transport, this.requestTimeoutSeconds, elicitationHandler);
    }

    @Override
    public McpClient createSse(String baseUri, String sseEndpoint,
            @Nullable Function<ElicitRequest, ElicitResult> elicitationHandler) {
        HttpClientSseClientTransport transport = HttpClientSseClientTransport.builder(baseUri)
                .jsonMapper(new JacksonMcpJsonMapper(new ObjectMapper()))
                .sseEndpoint(sseEndpoint)
                .build();
        return new DefaultMcpClient(baseUri, sseEndpoint, transport, this.requestTimeoutSeconds, elicitationHandler);
    }
}
