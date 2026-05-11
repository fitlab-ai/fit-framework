// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.engine.operators.models;

/**
 * 流式响应信息消费者。
 *
 * @author 刘信宏
 * @since 2024-05-17
 */
@FunctionalInterface
public interface StreamingConsumer<T, U> {
    /**
     * 消费流式响应数据。
     *
     * @param acc 表示聚合信息的 {@link T}。
     * @param chunk 表示单次流式响应信息 {@link U}。
     */
    void accept(T acc, U chunk);
}
