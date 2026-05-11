// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.chat.support;

import static org.fitframework.util.ObjectUtils.nullIf;

import org.fitframework.fel.core.chat.MessageType;
import org.fitframework.fel.core.tool.ToolCall;

import java.util.Collections;
import java.util.List;

/**
 * 表示大模型响应的 {@link org.fitframework.fel.core.chat.ChatMessage} 实现。
 *
 * @author 刘信宏
 * @since 2024-4-12
 */
public class AiMessage extends AbstractChatMessage {
    private final List<ToolCall> toolCalls;

    /**
     * 通过文本信息来初始化 {@link AiMessage} 的新实例。
     *
     * @param text 表示文本信息的 {@link String}。
     */
    public AiMessage(String text) {
        this(text, null);
    }

    /**
     * 通过文本信息和工具调用来初始化 {@link AiMessage} 的新实例。
     *
     * @param text 表示文本信息的 {@link String}。
     * @param toolCalls 表示工具调用列表的 {@link List}{@code <}{@link ToolCall}{@code >}。
     */
    public AiMessage(String text, List<ToolCall> toolCalls) {
        super(text);
        this.toolCalls = nullIf(toolCalls, Collections.emptyList());
    }

    @Override
    public MessageType type() {
        return MessageType.AI;
    }

    @Override
    public List<ToolCall> toolCalls() {
        return this.toolCalls;
    }

    @Override
    public String toString() {
        return this.isToolCall() ? "ai: " + this.toolCalls.toString() : super.toString();
    }
}
