// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.engine.activities;

import org.fitframework.fel.engine.flows.AiFlow;
import org.fitframework.waterflow.domain.flow.Flow;
import org.fitframework.inspection.Validation;

/**
 * AI 相关的节点的基类。
 *
 * @param <D> 表示处理的数据类型。
 * @param <RF> 表示 AI 流程内部装饰的流程类型，是 {@link Flow}{@code <}{@link D}{@code >} 的扩展。
 * @param <F> 表示 AI 流程的类型，是 {@link AiFlow}{@code <}{@link D}{@code , }{@link RF}{@code >} 的扩展。
 * @author 刘信宏
 * @since 2024-04-28
 */
public class AiActivity<D, RF extends Flow<D>, F extends AiFlow<D, RF>> {
    private final F flow;

    /**
     * AI 流程节点的构造方法。
     *
     * @param flow 表示 AI 流程对象的 {@link F}。
     * @throws IllegalArgumentException 当 {@code flow} 为 {@code null} 时。
     */
    protected AiActivity(F flow) {
        this.flow = Validation.notNull(flow, "Flow cannot be null.");
    }

    /**
     * 获取 AI 节点所对应的流程对象。
     *
     * @return AI 节点所对应的流程对象。
     */
    public F flow() {
        return this.flow;
    }
}
