// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.example.ai.chat.model;

import org.fitframework.fel.core.chat.ChatMessage;
import org.fitframework.fel.core.chat.ChatModel;
import org.fitframework.fel.core.chat.ChatOption;
import org.fitframework.fel.core.chat.support.ChatMessages;
import org.fitframework.fel.core.chat.support.HumanMessage;
import org.fitframework.http.annotation.GetMapping;
import org.fitframework.http.annotation.RequestMapping;
import org.fitframework.http.annotation.RequestParam;
import org.fitframework.annotation.Component;
import org.fitframework.annotation.Value;
import org.fitframework.flowable.Choir;

/**
 * 聊天模型样例控制器。
 *
 * @author 易文渊
 * @since 2024-08-29
 */
@Component
@RequestMapping("/ai/example")
public class ChatModelExampleController {
    private final ChatModel chatModel;
    @Value("${example.model}")
    private String modelName;

    public ChatModelExampleController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    /**
     * 聊天接口。
     *
     * @param query 表示用户输入查询的 {@link String}。
     * @return 表示聊天模型生成的回复的 {@link ChatMessage}。
     */
    @GetMapping("/chat")
    public ChatMessage chat(@RequestParam("query") String query) {
        ChatOption option = ChatOption.custom().model(this.modelName).stream(false).build();
        return this.chatModel.generate(ChatMessages.from(new HumanMessage(query)), option).first().block().get();
    }

    /**
     * 流式聊天接口。
     *
     * @param query 表示用户输入查询的 {@link String}。
     * @return 表示聊天模型生成的回复的 @{@link Choir}{@code <}{@link ChatMessage}{@code >}。
     */
    @GetMapping("/chat-stream")
    public Choir<ChatMessage> chatStream(@RequestParam("query") String query) {
        ChatOption option = ChatOption.custom().model(this.modelName).stream(true).build();
        return this.chatModel.generate(ChatMessages.from(new HumanMessage(query)), option);
    }
}