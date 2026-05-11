// SPDX-License-Identifier: MIT
// Copyright (c) 2026 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.example.ai.chat.memory;

import org.fitframework.fel.core.chat.ChatMessage;
import org.fitframework.fel.core.chat.ChatModel;
import org.fitframework.fel.core.chat.ChatOption;
import org.fitframework.fel.core.chat.support.ChatMessages;
import org.fitframework.fel.core.chat.support.HumanMessage;
import org.fitframework.fel.core.memory.Memory;
import org.fitframework.fel.core.memory.support.CacheMemory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 聊天记忆样例控制器（Spring Boot 版本）。
 *
 * @author 黄可欣
 * @since 2026-01-20
 */
@RestController
@RequestMapping("/ai/example")
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class ChatMemoryExampleController {
    private final ChatModel chatModel;
    private final Memory memory = new CacheMemory();
    @Value("${example.model}")
    private String modelName;

    public ChatMemoryExampleController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    /**
     * 聊天接口。
     *
     * @param query 表示用户输入查询的 {@link String}。
     * @return 表示聊天模型生成的回复的 {@link Map}{@code <}{@link String}{@code , }{@link Object}{@code >}。
     */
    @GetMapping("/chat")
    public Map<String, Object> chat(@RequestParam("query") String query) {
        ChatOption option = ChatOption.custom().model(this.modelName).stream(false).build();
        this.memory.add(new HumanMessage(query));
        ChatMessage aiMessage =
                this.chatModel.generate(ChatMessages.from(this.memory.messages()), option).first().block().get();
        this.memory.add(aiMessage);
        return Map.of("content", aiMessage.text(), "toolCalls", aiMessage.toolCalls());
    }
}