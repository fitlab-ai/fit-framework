// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.chat;

import org.fitframework.flowable.Choir;

/**
 * 表示聊天模型推理服务。
 *
 * @author 易文渊
 * @since 2024-04-13
 */
public interface ChatModel {
    /**
     * 调用聊天模型生成结果。
     *
     * @param prompt 表示提示词的 {@link Prompt}。
     * @param chatOption 表示聊天模型参数的 {@link ChatOption}。
     * @return 表示聊天模型生成结果的 {@link Choir}{@code <}{@link ChatMessage}{@code >}。
     */
    Choir<ChatMessage> generate(Prompt prompt, ChatOption chatOption);
}