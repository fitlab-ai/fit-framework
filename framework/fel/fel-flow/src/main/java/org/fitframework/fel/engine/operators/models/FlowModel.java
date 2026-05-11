// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.engine.operators.models;

import org.fitframework.fel.core.chat.ChatMessage;
import org.fitframework.fel.core.pattern.Model;
import org.fitframework.waterflow.bridge.fitflow.FitBoundedEmitter;

/**
 * 流式模型。
 *
 * @param <O> 表示对话模型的输出类型。
 * @author 刘信宏
 * @since 2024-04-16
 */
public interface FlowModel<I, O> extends Model<I, FitBoundedEmitter<O, ChatMessage>> {}
