// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.waterflow.domain.context.repo.flowcontext;

import org.fitframework.waterflow.domain.context.FlowContext;
import org.fitframework.log.Logger;

import java.util.List;

/**
 * 异步化节点间操作内存版实现类
 *
 * @author 高诗意
 * @since 1.0
 */
public class FlowContextMemoMessenger implements FlowContextMessenger {
    private static final Logger LOG = Logger.get(FlowContextMemoMessenger.class);

    /**
     * 发送事件到引擎外部
     *
     * @param nodeId 节点ID
     * @param contexts 流程实例执行过程产生的contexts
     * @param <I> 流程实例执行时的入参数据类型，用于泛型推倒
     */
    @Override
    public <I> void send(String nodeId, List<FlowContext<I>> contexts) {
        LOG.warn("FlowEngine memo messenger does not support sending events.");
    }

    /**
     * 发送回调函数事件到引擎外部
     *
     * @param contexts 流程实例执行过程产生的contexts
     * @param <I> 流程实例执行时的入参数据类型，用于泛型推倒
     */
    @Override
    public <I> void sendCallback(Object callback, List<FlowContext<I>> contexts) {
        LOG.warn("FlowEngine memo messenger does not support sending events.");
    }
}
