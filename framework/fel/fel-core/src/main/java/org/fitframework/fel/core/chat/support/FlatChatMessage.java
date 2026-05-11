// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.chat.support;

import org.fitframework.fel.core.chat.ChatMessage;
import org.fitframework.fel.core.chat.MessageType;
import org.fitframework.fel.core.tool.ToolCall;
import org.fitframework.inspection.Validation;
import org.fitframework.resource.web.Media;
import org.fitframework.util.ObjectUtils;
import org.fitframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 表示聊天消息的传输实现。
 *
 * @author 易文渊
 * @since 2024-04-12
 */
public class FlatChatMessage implements ChatMessage {
    private String id;
    private String type;
    private String text;
    private List<Media> medias;
    private List<ToolCall> toolCalls;

    /**
     * 根据{@link ChatMessage} 构造消息传输对象。
     *
     * @param chatMessage 提供构造参数的 {@link ChatMessage}。
     * @return 表示创建成功的 {@link FlatChatMessage}。
     */
    public static FlatChatMessage from(ChatMessage chatMessage) {
        Validation.notNull(chatMessage, "The chat message cannot be null.");
        if (chatMessage instanceof FlatChatMessage) {
            return (FlatChatMessage) chatMessage;
        }
        Validation.notNull(chatMessage.type(), "The message type cannot be null.");
        FlatChatMessage flatMessage = new FlatChatMessage();
        flatMessage.id = chatMessage.id().orElse(null);
        flatMessage.type = chatMessage.type().getRole();
        flatMessage.text = chatMessage.text();
        flatMessage.medias = chatMessage.medias();
        flatMessage.toolCalls = chatMessage.toolCalls();
        return flatMessage;
    }

    @Override
    public Optional<String> id() {
        return Optional.ofNullable(this.id);
    }

    @Override
    public MessageType type() {
        return MessageType.parse(this.type);
    }

    @Override
    public String text() {
        return ObjectUtils.nullIf(this.text, StringUtils.EMPTY);
    }

    @Override
    public List<Media> medias() {
        return ObjectUtils.nullIf(this.medias, Collections.emptyList());
    }

    @Override
    public List<ToolCall> toolCalls() {
        return ObjectUtils.nullIf(this.toolCalls, Collections.emptyList());
    }
}