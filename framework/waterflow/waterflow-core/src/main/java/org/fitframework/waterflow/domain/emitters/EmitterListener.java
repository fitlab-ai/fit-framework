// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.waterflow.domain.emitters;

/**
 * 数据发布的监听器
 *
 * @param <D> 待发布的数据类型
 * @param <S> session的类型
 * @since 1.0
 */
public interface EmitterListener<D, S> {
    /**
     * 处理发布过来的数据
     *
     * @param data 待处理的发布过来的数据
     * @param token 数据归属的session
     */
    void handle(D data, S token);

    /**
     * 订阅一个发布源
     *
     * @param emitter 发布源
     */
    default void offer(Emitter<D, S> emitter) {
        emitter.register(this);
    }
}
