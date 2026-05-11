// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.tool.mcp.client;

import org.fitframework.fel.tool.mcp.client.elicitation.ElicitRequest;
import org.fitframework.fel.tool.mcp.client.elicitation.ElicitResult;
import org.fitframework.inspection.Nullable;

import java.util.function.Function;

/**
 * Factory for creating {@link McpClient} instances with SSE or Streamable HTTP transport.
 * <p>Each client connects to a single MCP server.</p>
 *
 * @author 季聿阶
 * @since 2025-05-21
 */
public interface McpClientFactory {
    /**
     * Creates a client with streamable HTTP transport.
     *
     * @param baseUri The base URI of the MCP server.
     * @param sseEndpoint The SSE endpoint of the MCP server.
     * @param elicitationFunction The function to handle {@link ElicitRequest} and return {@link ElicitResult}.
     * If null, elicitation will not be supported in MCP client.
     * @return The created {@link McpClient} instance.
     */
    McpClient createStreamable(String baseUri, String sseEndpoint,
            @Nullable Function<ElicitRequest, ElicitResult> elicitationFunction);

    /**
     * Creates a client with SSE transport.
     *
     * @param baseUri The base URI of the MCP server.
     * @param sseEndpoint The SSE endpoint of the MCP server.
     * @param elicitationFunction The function to handle {@link ElicitRequest} and return {@link ElicitResult}.
     * If null, elicitation will not be supported in MCP client.
     * @return The created {@link McpClient} instance.
     */
    McpClient createSse(String baseUri, String sseEndpoint,
            @Nullable Function<ElicitRequest, ElicitResult> elicitationFunction);

    /**
     * Creates a client with streamable HTTP transport (default). No elicitation support.
     *
     * @param baseUri The base URI of the MCP server.
     * @param sseEndpoint The SSE endpoint of the MCP server.
     * @return The created {@link McpClient} instance.
     */
    default McpClient create(String baseUri, String sseEndpoint) {
        return this.createStreamable(baseUri, sseEndpoint, null);
    }

    /**
     * Creates a client with streamable HTTP transport. No elicitation support.
     *
     * @param baseUri The base URI of the MCP server.
     * @param sseEndpoint The SSE endpoint of the MCP server.
     * @return The created {@link McpClient} instance.
     */
    default McpClient createStreamable(String baseUri, String sseEndpoint) {
        return this.createStreamable(baseUri, sseEndpoint, null);
    }

    /**
     * Creates a client with SSE transport. No elicitation support.
     *
     * @param baseUri The base URI of the MCP server.
     * @param sseEndpoint The SSE endpoint of the MCP server.
     * @return The created {@link McpClient} instance.
     */
    default McpClient createSse(String baseUri, String sseEndpoint) {
        return this.createSse(baseUri, sseEndpoint, null);
    }
}