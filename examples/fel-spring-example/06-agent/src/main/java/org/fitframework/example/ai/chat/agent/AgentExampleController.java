// SPDX-License-Identifier: MIT
// Copyright (c) 2026 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.example.ai.chat.agent;

import static org.fitframework.util.CollectionUtils.asParent;

import org.fitframework.fel.core.chat.ChatMessage;
import org.fitframework.fel.core.chat.ChatModel;
import org.fitframework.fel.core.chat.ChatOption;
import org.fitframework.fel.core.tool.ToolInfo;
import org.fitframework.fel.core.util.Tip;
import org.fitframework.fel.engine.flows.AiFlows;
import org.fitframework.fel.engine.flows.AiProcessFlow;
import org.fitframework.fel.engine.operators.models.ChatFlowModel;
import org.fitframework.fel.engine.operators.patterns.support.DefaultAgent;
import org.fitframework.fel.engine.operators.prompts.Prompts;
import org.fitframework.fel.tool.service.ToolExecuteService;
import org.fitframework.fel.tool.service.ToolRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Agent 样例控制器（Spring Boot 版本）。
 *
 * @author 黄可欣
 * @since 2026-01-20
 */
@RestController
@RequestMapping("/ai/example")
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class AgentExampleController {
    private final AiProcessFlow<String, ChatMessage> agentFlow;
    private final ChatOption chatOption;
    private final ToolRepository toolRepository;

    public AgentExampleController(ChatModel chatModel, ToolExecuteService toolExecuteService,
            ToolRepository toolRepository, @Value("${example.model}") String modelName) {
        this.toolRepository = toolRepository;
        this.chatOption = ChatOption.custom().model(modelName).stream(false).build();
        DefaultAgent agent =
                new DefaultAgent(new ChatFlowModel(chatModel, this.chatOption), "example", toolExecuteService);
        this.agentFlow = AiFlows.<String>create()
                .map(query -> Tip.fromArray(query))
                .prompt(Prompts.human("{{0}}"))
                .delegate(agent)
                .close();
    }

    /**
     * 聊天接口。
     *
     * @param query 表示用户输入查询的 {@link String}。
     * @return 表示聊天模型生成的回复的 {@link Map}{@code <}{@link String}{@code , }{@link Object}{@code >}。
     */
    @GetMapping("/chat")
    public Map<String, Object> chat(@RequestParam("query") String query) {
        List<ToolInfo> toolInfos = asParent(toolRepository.listTool("example"));
        ChatMessage aiMessage = this.agentFlow.converse()
                .bind(ChatOption.custom(this.chatOption).tools(toolInfos).build())
                .offer(query)
                .await();
        return Map.of("content", aiMessage.text(), "toolCalls", aiMessage.toolCalls());
    }
}