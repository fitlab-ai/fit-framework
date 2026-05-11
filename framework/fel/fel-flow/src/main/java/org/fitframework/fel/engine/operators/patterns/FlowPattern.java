// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.engine.operators.patterns;

import org.fitframework.fel.core.pattern.Pattern;
import org.fitframework.waterflow.domain.context.FlowSession;
import org.fitframework.waterflow.domain.emitters.Emitter;
import org.fitframework.waterflow.domain.emitters.FlowEmitter;

/**
 * 流程委托单元。
 *
 * @param <I> 表示输入数据类型。
 * @param <O> 表示输出数据类型。
 * @author 刘信宏
 * @since 2024-04-22
 */
public interface FlowPattern<I, O> extends Pattern<I,FlowEmitter<O>>, Emitter<O, FlowSession> {}
