// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.tool.mcp.client.support.handler;

import io.modelcontextprotocol.spec.McpSchema;
import org.fitframework.log.Logger;

/**
 * Handles MCP client messages received from MCP server,
 * including logging notifications and elicitation requests.
 *
 * @author 黄可欣
 * @since 2025-11-03
 */
public class McpClientLogHandler {
    private static final Logger log = Logger.get(McpClientLogHandler.class);
    private final String clientId;

    /**
     * Constructs a new instance of McpClientLogHandler.
     *
     * @param clientId The unique identifier of the MCP client.
     */
    public McpClientLogHandler(String clientId) {
        this.clientId = clientId;
    }

    /**
     * Handles logging messages received from the MCP server.
     * Includes the client UUID in the log message for tracking.
     *
     * @param notification The {@link McpSchema.LoggingMessageNotification} containing the log level and data.
     */
    public void handleLoggingMessage(McpSchema.LoggingMessageNotification notification) {
        log.info("Received logging message from MCP server. [clientId={}, level={}, data={}]",
                this.clientId,
                notification.level(),
                notification.data());
    }
}
