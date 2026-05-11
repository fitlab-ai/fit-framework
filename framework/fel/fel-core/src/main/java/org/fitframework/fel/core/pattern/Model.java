// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.pattern;

/**
 * 模型算子的基类。
 *
 * @param <I> 表示模型算子的输入类型。
 * @param <O> 表示模型算子的输出类型。
 * @author 刘信宏
 * @since 2024-06-11
 */
public interface Model<I, O> extends Pattern<I, O> {}
