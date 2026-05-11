// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.waterflow.domain.stream;

/**
 * 新数据发射器
 *
 * @param <T> 新数据类型
 * @since 1.0
 */
public interface Collector<T> {
    /**
     * 发射一条新数据
     *
     * @param data 待发射的数据
     */
    void collect(T data);
}
