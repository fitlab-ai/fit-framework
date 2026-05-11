// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.example.ai.chat.template;

import org.fitframework.fel.core.chat.ChatMessage;
import org.fitframework.fel.core.chat.ChatModel;
import org.fitframework.fel.core.chat.ChatOption;
import org.fitframework.fel.core.chat.support.ChatMessages;
import org.fitframework.fel.core.template.MessageTemplate;
import org.fitframework.fel.core.template.support.HumanMessageTemplate;
import org.fitframework.fel.core.util.Tip;
import org.fitframework.http.annotation.GetMapping;
import org.fitframework.http.annotation.RequestMapping;
import org.fitframework.http.annotation.RequestParam;
import org.fitframework.annotation.Component;
import org.fitframework.annotation.Value;

/**
 * 聊天模板样例控制器。
 *
 * @author 易文渊
 * @since 2024-08-29
 */
@Component
@RequestMapping("/ai/example")
public class ChatTemplateExampleController {
    private final ChatModel chatModel;
    private final MessageTemplate template;
    @Value("${example.model}")
    private String modelName;

    public ChatTemplateExampleController(ChatModel chatModel) {
        this.chatModel = chatModel;
        this.template = new HumanMessageTemplate("给我讲个关于{{adjective}}的{{content}}。");
    }

    /**
     * 聊天接口。
     *
     * @param adjective 表示主题的 {@link String}。
     * @param content 表示内容的 {@link String}。
     * @return 表示聊天模型生成的回复的 {@link ChatMessage}。
     */
    @GetMapping("/chat")
    public ChatMessage chat(@RequestParam("adjective") String adjective, @RequestParam("content") String content) {
        ChatOption option = ChatOption.custom().model(this.modelName).stream(false).build();
        return this.chatModel.generate(ChatMessages.from(this.template.render(Tip.from("adjective", adjective)
                .add("content", content)
                .freeze())), option).first().block().get();
    }
}