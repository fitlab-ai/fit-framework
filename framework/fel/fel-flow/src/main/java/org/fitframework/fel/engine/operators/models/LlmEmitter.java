// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.engine.operators.models;

import org.fitframework.fel.core.chat.ChatMessage;
import org.fitframework.fel.core.chat.Prompt;
import org.fitframework.fel.core.chat.support.HumanMessage;
import org.fitframework.fel.core.memory.Memory;
import org.fitframework.fel.engine.util.StateKey;
import org.fitframework.waterflow.bridge.fitflow.FitBoundedEmitter;
import org.fitframework.waterflow.domain.context.FlowSession;
import org.fitframework.flowable.Publisher;
import org.fitframework.inspection.Validation;
import org.fitframework.util.ObjectUtils;
import org.fitframework.util.StringUtils;

/**
 * 流式模型发射器。
 *
 * @author 刘信宏
 * @since 2024-05-16
 */
public class LlmEmitter<O extends ChatMessage> extends FitBoundedEmitter<O, ChatMessage> {
    private static final StreamingConsumer<ChatMessage, ChatMessage> EMPTY_CONSUMER = (acc, chunk) -> {};

    private final ChatChunk chunkAcc = new ChatChunk();
    private final StreamingConsumer<ChatMessage, ChatMessage> consumer;
    private final Memory memory;
    private final ChatMessage question;

    /**
     * 初始化 {@link LlmEmitter}。
     *
     * @param publisher 表示数据发布者的 {@link Publisher}{@code <}{@link O}{@code >}。
     * @param prompt 表示模型输入的 {@link Prompt}， 用于获取默认用户问题。
     * @param session 表示流程实例运行标识的 {@link FlowSession}。
     */
    public LlmEmitter(Publisher<O> publisher, Prompt prompt, FlowSession session) {
        super(publisher, data -> data);
        Validation.notNull(session, "The session cannot be null.");
        this.consumer = ObjectUtils.nullIf(session.getInnerState(StateKey.STREAMING_CONSUMER), EMPTY_CONSUMER);
        this.memory = session.getInnerState(StateKey.HISTORY);
        this.question =
                ObjectUtils.getIfNull(session.getInnerState(StateKey.HISTORY_INPUT), () -> getDefaultQuestion(prompt));
    }

    @Override
    public void emit(ChatMessage data, FlowSession trans) {
        super.emit(data, this.flowSession);
        this.chunkAcc.merge(data);
        this.consumer.accept(this.chunkAcc, data);
    }

    @Override
    public void complete() {
        if (this.memory != null && this.chunkAcc.toolCalls().isEmpty()) {
            this.memory.add(this.question);
            this.memory.add(this.chunkAcc);
        }
        super.complete();
    }

    private static ChatMessage getDefaultQuestion(Prompt prompt) {
        int size = prompt.messages().size();
        if (size == 0) {
            return new HumanMessage(StringUtils.EMPTY);
        }
        return prompt.messages().get(size - 1);
    }
}
