// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.template;

import org.fitframework.fel.core.chat.ChatMessage;
import org.fitframework.fel.core.document.Content;

import java.util.Map;

/**
 * 消息模板接口定义。
 *
 * @author 易文渊
 * @since 2024-04-25
 */
public interface MessageTemplate extends GenericTemplate<Map<String, Content>, ChatMessage> {}