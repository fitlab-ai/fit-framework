// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.engine.activities;

import org.fitframework.fel.engine.activities.processors.AiBranchProcessor;
import org.fitframework.fel.engine.activities.processors.AiBranchToProcessor;
import org.fitframework.fel.engine.flows.AiFlow;
import org.fitframework.waterflow.domain.flow.Flow;
import org.fitframework.waterflow.domain.states.MatchHappen;
import org.fitframework.waterflow.domain.states.MatchToHappen;
import org.fitframework.waterflow.domain.states.State;
import org.fitframework.waterflow.domain.stream.operators.Operators;
import org.fitframework.inspection.Validation;

/**
 * 条件跳转分支。
 *
 * @param <D> 表示当前节点所属流程的初始数据类型。
 * @param <I> 表示入参数据类型。
 * @param <RF> 表示内部的数据流程类型，是 {@link Flow}{@code <}{@link D}{@code >} 的扩展。
 * @param <F> AI 流程类型，是 {@link AiFlow}{@code <}{@link D}{@code , }{@link RF}{@code >} 的扩展。
 * @author 刘信宏
 * @since 2024-04-28
 */
public class AiMatchToHappen<D, I, RF extends Flow<D>, F extends AiFlow<D, RF>> {
    private final MatchToHappen<D, I, RF> matchToHappen;

    private final F flow;

    /**
     * 创建一个 {@link AiMatchToHappen} 对象。
     *
     * @param matchToHappen 匹配条件。
     * @param flow AI 流程。
     */
    public AiMatchToHappen(MatchToHappen<D, I, RF> matchToHappen, F flow) {
        this.matchToHappen = Validation.notNull(matchToHappen, "matchToHappen cannot be null.");
        this.flow = Validation.notNull(flow, "Flow cannot be null.");
    }

    /**
     * 指定条件和对应的处理器创建条件跳转分支。
     *
     * @param whether 表示匹配条件的 {@link Operators.Whether}{@code <}{@link I}{@code >}。
     * @param processor 表示分支跳转处理器的 {@link AiBranchToProcessor}{@code <}{@link D}{@code , }{@link I}{@code ,
     * }{@link RF}{@code , }{@link F}{@code >}。
     * @return 表示条件分支的 {@link AiMatchToHappen}{@code <}{@link D}{@code , }{@link I}{@code , }{@link RF}{@code ,
     * }{@link F}{@code >}。
     * @throws IllegalArgumentException 当 {@code processor} 为 {@code null} 时。
     */
    public AiMatchToHappen<D, I, RF, F> matchTo(Operators.Whether<I> whether,
            AiBranchToProcessor<D, I, RF, F> processor) {
        Validation.notNull(processor, "Ai branchTo processor cannot be null.");
        this.matchToHappen.matchTo(whether, node -> processor.process(new AiState<>(node, this.flow)));
        return this;
    }

    /**
     * 指定条件和对应的处理器创建条件分支。
     *
     * @param whether 表示匹配条件的 {@link Operators.Whether}{@code <}{@link I}{@code >}。
     * @param processor 表示分支处理器的 {@link AiBranchProcessor}{@code <}{@link O}{@code , }{@link D}{@code ,
     * }{@link I}{@code , }{@link RF}{@code , }{@link F}{@code >}。
     * @param <O> 表示第一个条件分支指定的返回类型。
     * @return 表示条件分支的 {@link AiMatchHappen}{@code <}{@link O}{@code , }{@link D}{@code , }{@link I}{@code ,
     * }{@link RF}{@code , }{@link F}{@code >}。
     * @throws IllegalArgumentException 当 {@code processor} 为 {@code null} 时。
     */
    public <O> AiMatchHappen<O, D, I, RF, F> match(Operators.Whether<I> whether,
            AiBranchProcessor<O, D, I, RF, F> processor) {
        Validation.notNull(processor, "Ai branch processor cannot be null.");
        MatchHappen<O, D, I, RF> match =
                this.matchToHappen.match(whether, node -> processor.process(new AiState<>(node, this.flow)).state);
        return new AiMatchHappen<>(match, this.flow);
    }

    /**
     * 提供一个默认的处理逻辑，并结束条件节点。
     *
     * @param processor 表示分支处理器的 {@link AiBranchProcessor}{@code <}{@link O}{@code , }{@link D}{@code ,?,
     * }{@link RF}{@code , }{@link F}{@code >}。
     * @param <O> 表示第一个条件分支指定的返回类型。
     * @return 表示条件节点的 {@link AiState}{@code <}{@link O}{@code , }{@link D}{@code , }{@link O}{@code ,
     * }{@link RF}{@code , }{@link F}{@code >}。
     * @throws IllegalArgumentException 当 {@code processor} 为 {@code null} 时。
     */
    public <O> AiState<O, D, ?, RF, F> others(AiBranchProcessor<O, D, I, RF, F> processor) {
        Validation.notNull(processor, "Ai branch processor cannot be null.");
        State<O, D, ?, RF> others =
                this.matchToHappen.others(node -> processor.process(new AiState<>(node, this.flow)).state);

        return new AiState<>(others, this.flow);
    }
}
