// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.tool.mcp.server.config;

import io.modelcontextprotocol.json.McpJsonMapper;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.spec.McpSchema;
import org.fitframework.fel.tool.mcp.server.FitMcpServer;
import org.fitframework.fel.tool.mcp.server.transport.FitMcpSseServerTransportProvider;
import org.fitframework.fel.tool.service.ToolChangedObserverRegistry;
import org.fitframework.fel.tool.service.ToolExecuteService;
import org.fitframework.annotation.Bean;
import org.fitframework.annotation.Component;
import org.fitframework.annotation.Fit;
import org.fitframework.annotation.Value;

import java.time.Duration;

/**
 * MCP SSE Server Bean implemented with MCP SDK.
 *
 * @author 黄可欣
 * @since 2025-11-10
 */
@Component
public class McpSseServerConfig {
    @Bean
    public FitMcpSseServerTransportProvider fitMcpSseServerTransportProvider(
            @Value("${mcp.server.ping.interval-seconds}") int keepAliveIntervalSeconds) {
        return FitMcpSseServerTransportProvider.builder()
                .jsonMapper(McpJsonMapper.getDefault())
                .keepAliveInterval(Duration.ofSeconds(keepAliveIntervalSeconds))
                .build();
    }

    @Bean("McpSyncSseServer")
    public McpSyncServer mcpSyncSseServer(FitMcpSseServerTransportProvider transportProvider,
            @Value("${mcp.server.request.timeout-seconds}") int requestTimeoutSeconds) {
        return McpServer.sync(transportProvider)
                .serverInfo("FIT Store MCP SSE Server", "3.7.0-SNAPSHOT")
                .capabilities(McpSchema.ServerCapabilities.builder().tools(true).logging().build())
                .requestTimeout(Duration.ofSeconds(requestTimeoutSeconds))
                .build();
    }

    @Bean("McpSseServer")
    public FitMcpServer defaultMcpSseServer(ToolExecuteService toolExecuteService,
            @Fit(alias = "McpSyncSseServer") McpSyncServer mcpSyncServer,
            ToolChangedObserverRegistry toolChangedObserverRegistry) {
        return new FitMcpServer(toolExecuteService, mcpSyncServer, toolChangedObserverRegistry);
    }
}
