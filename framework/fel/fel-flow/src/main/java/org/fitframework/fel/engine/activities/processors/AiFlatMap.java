// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.engine.activities.processors;

import org.fitframework.fel.engine.activities.AiDataStart;

/**
 * 将每个数据转换为一个数据流，并往下发射流转。
 *
 * @param <T> 表示输入数据类型。
 * @param <R> 表示数据流的数据类型。
 * @author 夏斐
 * @since 2024-05-18
 */
@FunctionalInterface
public interface AiFlatMap<T, R> {
    /**
     * process
     *
     * @param input 表示输入数据的 {@link T}。
     * @return 表示数据前置开始节点的 {@link AiDataStart}{@code <}{@link R}{@code , }{@link R}{@code , ?>}。
     */
    AiDataStart<R, R, ?> process(T input);
}
