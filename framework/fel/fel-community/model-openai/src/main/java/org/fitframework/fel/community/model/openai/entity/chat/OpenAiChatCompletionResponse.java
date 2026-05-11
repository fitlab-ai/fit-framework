// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.community.model.openai.entity.chat;

import org.fitframework.fel.core.chat.ChatMessage;
import org.fitframework.fel.core.chat.support.AiMessage;
import org.fitframework.fel.core.tool.ToolCall;
import org.fitframework.annotation.Alias;
import org.fitframework.annotation.Aliases;
import org.fitframework.util.CollectionUtils;
import org.fitframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * OpenAi API 格式的会话补全响应。
 *
 * @author 易文渊
 * @author 张庭怿
 * @since 2024-4-30
 */
public class OpenAiChatCompletionResponse {
    private static final ChatMessage EMPTY_RESPONSE = new AiMessage(StringUtils.EMPTY);

    private List<OpenAiChatCompletionChoice> choices;

    /**
     * 获取响应中的消息。
     *
     * @return 表示模型回复的 {@link ChatMessage}。
     */
    public ChatMessage message() {
        return extractMessage(OpenAiChatMessage::content, OpenAiChatMessage::toolCalls);
    }

    /**
     * 获取响应中的模型推理。
     *
     * @return 表示模型回复的 {@link ChatMessage}。
     */
    public ChatMessage reasoningContent() {
        return extractMessage(OpenAiChatMessage::reasoningContent, OpenAiChatMessage::toolCalls);
    }

    private ChatMessage extractMessage(
            Function<OpenAiChatMessage, Object> contentExtractor,
            Function<OpenAiChatMessage, List<ToolCall>> toolCallsExtractor) {
        if (CollectionUtils.isEmpty(choices)) {
            return EMPTY_RESPONSE;
        }
        OpenAiChatMessage openAiChatMessage = choices.get(0).message;
        if (openAiChatMessage == null) {
            return EMPTY_RESPONSE;
        }

        String content = Optional.ofNullable(contentExtractor.apply(openAiChatMessage))
                .filter(obj -> obj instanceof String)
                .map(obj -> (String) obj)
                .orElse(StringUtils.EMPTY);

        List<ToolCall> toolCalls = Optional.ofNullable(toolCallsExtractor.apply(openAiChatMessage))
                .orElse(Collections.emptyList());

        return new AiMessage(content, toolCalls);
    }

    /**
     * 模型响应消息。
     */
    public static class OpenAiChatCompletionChoice {
        @Aliases(@Alias("delta"))
        private OpenAiChatMessage message;
    }
}
