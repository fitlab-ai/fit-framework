// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.tool;

/**
 * 表示工具调用请求的实体片段。
 *
 * @author 刘信宏
 * @since 2024-12-23
 */
public interface ToolCallChunk extends ToolCall {
    /**
     * 合并工具调用的流式报文。
     *
     * @param toolCall 表示工具调用请求实体的 {@link ToolCall}。
     */
    void merge(ToolCall toolCall);
}
