// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
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
import org.fitframework.http.annotation.GetMapping;
import org.fitframework.http.annotation.RequestMapping;
import org.fitframework.http.annotation.RequestParam;
import org.fitframework.annotation.Component;
import org.fitframework.annotation.Value;

import java.util.List;

/**
 * Agent 样例控制器。
 *
 * @author 易文渊
 * @since 2024-09-02
 */
@Component
@RequestMapping("/ai/example")
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
     * @return 表示聊天模型生成的回复的 {@link ChatMessage}。
     */
    @GetMapping("/chat")
    public ChatMessage chat(@RequestParam("query") String query) {
        List<ToolInfo> toolInfos = asParent(toolRepository.listTool("example"));
        return this.agentFlow.converse()
                .bind(ChatOption.custom(this.chatOption).tools(toolInfos).build())
                .offer(query)
                .await();
    }
}