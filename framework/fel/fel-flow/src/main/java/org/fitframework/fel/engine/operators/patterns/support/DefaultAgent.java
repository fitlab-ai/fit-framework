// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.engine.operators.patterns.support;

import static org.fitframework.inspection.Validation.notBlank;
import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.fel.core.chat.ChatMessage;
import org.fitframework.fel.core.chat.Prompt;
import org.fitframework.fel.core.chat.support.ChatMessages;
import org.fitframework.fel.core.chat.support.ToolMessage;
import org.fitframework.fel.core.tool.ToolCall;
import org.fitframework.fel.engine.operators.models.ChatFlowModel;
import org.fitframework.fel.engine.operators.patterns.AbstractAgent;
import org.fitframework.fel.tool.service.ToolExecuteService;
import org.fitframework.waterflow.domain.context.StateContext;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 表示智能体的默认实现。
 *
 * @author 易文渊
 * @since 2024-09-02
 */
public class DefaultAgent extends AbstractAgent {
    private final String namespace;
    private final ToolExecuteService toolExecuteService;

    /**
     * 创建一个智能体。
     *
     * @param flowModel 智能体的流程模型。
     * @param namespace 智能体的命名空间。
     * @param toolExecuteService 智能体的工具执行服务。
     */
    public DefaultAgent(ChatFlowModel flowModel, String namespace, ToolExecuteService toolExecuteService) {
        super(flowModel);
        this.namespace = notBlank(namespace, "The namespace cannot be blank.");
        this.toolExecuteService = notNull(toolExecuteService, "The tool execute service cannot be null.");
    }

    @Override
    protected Prompt doToolCall(List<ToolCall> toolCalls, StateContext ctx) {
        return toolCalls.stream().map(toolCall -> {
            String text = this.toolExecuteService.execute(this.namespace, toolCall.name(), toolCall.arguments());
            return (ChatMessage) new ToolMessage(toolCall.id(), text);
        }).collect(Collectors.collectingAndThen(Collectors.toList(), ChatMessages::from));
    }
}