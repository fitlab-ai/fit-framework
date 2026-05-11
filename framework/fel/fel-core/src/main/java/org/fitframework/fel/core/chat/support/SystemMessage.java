// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.chat.support;

import org.fitframework.fel.core.chat.ChatMessage;
import org.fitframework.fel.core.chat.MessageType;

/**
 * 表现系统消息的 {@link ChatMessage} 实现。
 *
 * @author 刘信宏
 * @author 易文渊
 * @since 2024-4-12
 */
public class SystemMessage extends AbstractChatMessage {
    /**
     * 通过文本信息来初始化 {@link SystemMessage} 的新实例。
     *
     * @param text 表示文本信息的 {@link String}。
     */
    public SystemMessage(String text) {
        super(text);
    }

    @Override
    public MessageType type() {
        return MessageType.SYSTEM;
    }
}
