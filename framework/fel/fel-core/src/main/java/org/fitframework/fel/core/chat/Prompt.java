// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.chat;

import org.fitframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 表示聊天大模型输入的接口。
 *
 * @author 易文渊
 * @since 2024-04-16
 */
public interface Prompt {
    /**
     * 获取输入的聊天消息列表。
     *
     * @return 表示聊天消息列表的 {@link List}{@code <}{@link ChatMessage}{@code >}。
     */
    List<ChatMessage> messages();

    /**
     * 获取文本内容。
     *
     * @return 返回表示文本内容的 {@link String}。
     */
    default String text() {
        return Optional.ofNullable(this.messages())
                .map(msg -> msg.stream().map(ChatMessage::text).collect(Collectors.joining("\n")))
                .orElse(StringUtils.EMPTY);
    }
}