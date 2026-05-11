// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.tool.mcp.entity;

/**
 * Represents different types of events used in MCP.
 *
 * @author 季聿阶
 * @since 2025-05-22
 */
public enum Event {
    /**
     * Represents an endpoint event.
     */
    ENDPOINT("endpoint"),

    /**
     * Represents a message event.
     */
    MESSAGE("message");

    private final String code;

    /**
     * Constructor to initialize the event with a specific code.
     *
     * @param code The code associated with the event.
     */
    Event(String code) {
        this.code = code;
    }

    /**
     * Returns the code associated with the event.
     *
     * @return The code of the event.
     */
    public String code() {
        return this.code;
    }
}