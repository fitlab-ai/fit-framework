// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.engine.operators.prompts;

import org.fitframework.fel.core.chat.Prompt;
import org.fitframework.fel.core.pattern.Pattern;

/**
 * 提示词模板接口。
 *
 * @param <T> 表示提示词模板入参的类型。
 * @author 刘信宏
 * @since 2024-04-12
 */
public interface PromptTemplate<T> extends Pattern<T, Prompt> {}
