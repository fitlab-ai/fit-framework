// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.engine.operators.models;

import static org.fitframework.inspection.Validation.notNull;
import static org.fitframework.util.ObjectUtils.nullIf;

import org.fitframework.fel.core.chat.ChatMessage;
import org.fitframework.fel.core.chat.ChatModel;
import org.fitframework.fel.core.chat.ChatOption;
import org.fitframework.fel.core.chat.Prompt;
import org.fitframework.fel.engine.util.AiFlowSession;
import org.fitframework.fel.engine.util.StateKey;
import org.fitframework.waterflow.bridge.fitflow.FitBoundedEmitter;
import org.fitframework.waterflow.domain.context.FlowSession;
import org.fitframework.flowable.Choir;
import org.fitframework.util.ObjectUtils;

/**
 * 流式对话模型实现。
 *
 * @author 刘信宏
 * @since 2024-05-16
 */
public class ChatFlowModel implements FlowModel<Prompt, ChatMessage> {
    private final ChatModel chatModel;

    private final ChatOption option;

    /**
     * 创建一个流式对话模型。
     *
     * @param chatModel 对话模型。
     * @param option 对话参数。
     */
    public ChatFlowModel(ChatModel chatModel, ChatOption option) {
        this.chatModel = notNull(chatModel, "The model provider can not be null.");
        this.option = option;
    }

    /**
     * 绑定模型超参数。
     *
     * @param option 表示模型超参数的 {@link ChatOption}。
     * @return 表示绑定了超参数的 {@link ChatModel}。
     * @throws IllegalArgumentException 当 {@code options} 为 {@code null} 时。
     */
    public ChatFlowModel bind(ChatOption option) {
        notNull(option, "The chat options cannot be null.");
        return new ChatFlowModel(this.chatModel, option);
    }

    @Override
    public FitBoundedEmitter<ChatMessage, ChatMessage> invoke(Prompt input) {
        notNull(input, "The model input data can not be null.");
        FlowSession session =
                AiFlowSession.get().orElseThrow(() -> new IllegalStateException("The ai session cannot be empty."));
        ChatOption dynamicOption = nullIf(session.getInnerState(StateKey.CHAT_OPTION), this.option);
        notNull(dynamicOption, "The chat options can not be null.");
        Choir<ChatMessage> choir = ObjectUtils.cast(this.chatModel.generate(input, dynamicOption));
        return new LlmEmitter<>(choir, input, session);
    }
}
