// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.waterflow.domain.events;

import lombok.Getter;
import org.fitframework.waterflow.domain.context.FlowContext;
import org.fitframework.event.Event;

import java.util.List;

/**
 * 回调函数事件类
 *
 * @author 李哲峰
 * @since 1.0
 */
@Getter
public class FlowCallbackEvent<O> implements Event {
    private final List<FlowContext<O>> flowContexts;

    private final Object publisher;

    /**
     * 构造函数
     *
     * @param flowContexts 回调函数参数
     * @param publisher 发布者
     */
    public FlowCallbackEvent(List<FlowContext<O>> flowContexts, Object publisher) {
        this.flowContexts = flowContexts;
        this.publisher = publisher;
    }

    @Override
    public Object publisher() {
        return this.publisher;
    }
}
