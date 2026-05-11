// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.tool.mcp.client.elicitation;

import java.util.Map;

/**
 * Represents an elicitation request from an MCP server.
 * This is a simplified version that doesn't depend on MCP SDK types.
 *
 * @param message The {@link String} message describing what information is needed from the user.
 * @param requestedSchema The {@link Map}{@code <}{@link String}{@code , }{@link Object}{@code >} JSON schema defining
 * the elicitation request data structure.
 * @author 黄可欣
 * @see <a href=https://modelcontextprotocol.io/specification/2025-06-18/client/elicitation#protocol-messages>MCP
 * Protocol</a>
 * @since 2025-11-25
 */
public record ElicitRequest(String message, Map<String, Object> requestedSchema) {}
