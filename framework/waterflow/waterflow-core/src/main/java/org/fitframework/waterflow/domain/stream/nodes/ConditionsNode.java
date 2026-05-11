// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.waterflow.domain.stream.nodes;

import org.fitframework.waterflow.domain.context.FlowContext;
import org.fitframework.waterflow.domain.context.repo.flowcontext.FlowContextMessenger;
import org.fitframework.waterflow.domain.context.repo.flowcontext.FlowContextRepo;
import org.fitframework.waterflow.domain.context.repo.flowlock.FlowLocks;
import org.fitframework.waterflow.domain.enums.FlowNodeType;
import org.fitframework.waterflow.domain.stream.callbacks.PreSendCallbackInfo;
import org.fitframework.waterflow.domain.stream.operators.Operators;
import org.fitframework.waterflow.domain.stream.reactive.Subscription;
import org.fitframework.waterflow.domain.utils.UUIDUtil;
import org.fitframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 条件节点，也是match的起始节点
 * 辉子 2019-12-17
 *
 * @param <I>传入数据类型
 * @author 高诗意
 * @since 1.0
 */
public class ConditionsNode<I> extends Node<I, I> {
    /**
     * 1->1处理节点
     *
     * @param streamId stream流程ID
     * @param processor 对应处理器
     * @param repo 上下文持久化repo，默认在内存
     * @param messenger 上下文事件发送器，默认在内存
     * @param locks 流程锁
     */
    public ConditionsNode(String streamId, Operators.Just<FlowContext<I>> processor, FlowContextRepo repo,
            FlowContextMessenger messenger, FlowLocks locks) {
        super(streamId, i -> {
            processor.process(i);
            return i.getData();
        }, repo, messenger, locks, () -> initFrom(streamId, repo, messenger, locks));
    }

    /**
     * 1->1处理节点
     *
     * @param streamId stream流程ID
     * @param nodeId stream流程节点ID
     * @param processor 对应处理器
     * @param repo 上下文持久化repo，默认在内存
     * @param messenger 上下文事件发送器，默认在内存
     * @param locks 流程锁
     * @param nodeType 节点类型
     */
    public ConditionsNode(String streamId, String nodeId, Operators.Just<FlowContext<I>> processor,
            FlowContextRepo repo, FlowContextMessenger messenger, FlowLocks locks, FlowNodeType nodeType) {
        this(streamId, processor, repo, messenger, locks);
        this.id = nodeId;
        this.nodeType = nodeType;
    }

    /**
     * 只publish给符合条件的subscription
     *
     * @param streamId id
     * @param repo 持久化
     * @param messenger 事件发送器
     * @param locks 流程锁
     * @param <I> 数据类型
     * @return From 数据publisher
     */
    private static <I> From<I> initFrom(String streamId, FlowContextRepo repo, FlowContextMessenger messenger,
            FlowLocks locks) {
        return new ConditionFrom<>(streamId, repo, messenger, locks);
    }

    private static class ConditionFrom<I> extends From<I> {
        public ConditionFrom(String streamId, FlowContextRepo repo, FlowContextMessenger messenger, FlowLocks locks) {
            super(streamId, repo, messenger, locks);
        }

        @Override
        public void offer(List<FlowContext<I>> contexts, Consumer<PreSendCallbackInfo<I>> preSendCallback) {
            Map<Subscription<I>, List<FlowContext<I>>> matchedContexts = new LinkedHashMap<>();
            this.getSubscriptions().forEach(w -> {
                List<FlowContext<I>> matched = contexts.stream()
                        .filter(c -> w.getWhether().is(c.getData()))
                        .peek(c -> c.setNextPositionId(w.getId()))
                        .collect(Collectors.toList());
                matched.forEach(contexts::remove);
                matchedContexts.put(w, matched);
            });
            PreSendCallbackInfo<I> callbackInfo = new PreSendCallbackInfo<>(matchedContexts, contexts);
            preSendCallback.accept(callbackInfo);
            matchedContexts.forEach((subscription, matched) -> {
                // For order-sensitive data, directly synchronously executes the next conditional branch node.
                if (CollectionUtils.isNotEmpty(matched) && matched.get(0).getSession().preserved()) {
                    subscription.process(matched);
                } else {
                    subscription.cache(matched);
                }
            });
        }

    }
}
