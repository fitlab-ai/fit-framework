// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.engine.operators.models;

import org.fitframework.fel.core.chat.ChatMessage;
import org.fitframework.fel.core.chat.Prompt;
import org.fitframework.fel.core.chat.support.AiMessage;
import org.fitframework.fel.core.chat.support.ChatMessages;
import org.fitframework.fel.core.memory.Memory;
import org.fitframework.fel.core.tool.ToolCall;
import org.fitframework.fel.engine.util.StateKey;
import org.fitframework.waterflow.domain.context.FlowSession;
import org.fitframework.flowable.Choir;
import org.fitframework.util.StringUtils;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 表示 {@link LlmEmitter} 的测试。
 *
 * @author 宋永坦
 * @since 2025-07-05
 */
class LlmEmitterTest {
    @Test
    void shouldAddMemoryWhenCompleteGivenLlmOutput() {
        String output = "data1";
        Prompt prompt = ChatMessages.fromList(Collections.emptyList());
        Choir<ChatMessage> dataSource = Choir.create(emitter -> {
            emitter.emit(new AiMessage(output));
            emitter.complete();
        });
        FlowSession flowSession = new FlowSession();
        Memory mockMemory = Mockito.mock(Memory.class);
        ArgumentCaptor<ChatMessage> captor = ArgumentCaptor.forClass(ChatMessage.class);
        Mockito.doNothing().when(mockMemory).add(captor.capture());
        flowSession.setInnerState(StateKey.HISTORY, mockMemory);

        LlmEmitter<ChatMessage> llmEmitter = new LlmEmitter<>(dataSource, prompt, flowSession);
        llmEmitter.start(flowSession);

        List<ChatMessage> captured = captor.getAllValues();
        assertEquals(2, captured.size());
        assertEquals(StringUtils.EMPTY, captured.get(0).text());
        assertEquals(output, captured.get(1).text());
    }

    @Test
    void shouldNotAddMemoryWhenCompleteGivenLlmToolCallOutput() {
        String output = "data1";
        Prompt prompt = ChatMessages.fromList(Collections.emptyList());
        Choir<ChatMessage> dataSource = Choir.create(emitter -> {
            emitter.emit(new AiMessage(output, Arrays.asList(ToolCall.custom().id("id1").build())));
            emitter.complete();
        });
        FlowSession flowSession = new FlowSession();
        Memory mockMemory = Mockito.mock(Memory.class);
        flowSession.setInnerState(StateKey.HISTORY, mockMemory);

        LlmEmitter<ChatMessage> llmEmitter = new LlmEmitter<>(dataSource, prompt, flowSession);
        llmEmitter.start(flowSession);

        Mockito.verify(mockMemory, Mockito.times(0)).add(Mockito.any());
    }
}