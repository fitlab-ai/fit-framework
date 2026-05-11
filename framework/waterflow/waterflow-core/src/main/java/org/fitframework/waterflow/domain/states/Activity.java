// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.waterflow.domain.states;

import lombok.Getter;
import org.fitframework.waterflow.domain.flow.Flow;

/**
 * 所有activity基类
 *
 * @param <D> 这个节点对应flow的初始数据类型
 * @param <F> 对应的是处理流，还是生产流，用于泛型推演
 * @since 1.0
 */
public abstract class Activity<D, F extends Flow<D>> {
    @Getter
    private final F flow;

    /**
     * 构造函数
     *
     * @param flow 流对象
     */
    protected Activity(F flow) {
        this.flow = flow;
    }

    /**
     * 标识一个节点的别名id
     * 设定一个别名id后，通常用于to跳转，或者向一个该节点发射数据
     *
     * @param id 设置的id
     * @return 节点本身
     */
    protected Activity<D, F> setId(String id) {
        this.flow.tagNode(id, this);
        return this;
    }
}
