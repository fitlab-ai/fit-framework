// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.waterflow.domain.stream.reactive;

import java.util.List;

/**
 * 用于流结束后的返回对象处理
 *
 * @param <O> 处理的对象类型
 * @since 1.0
 */
public interface Callback<O> {
    /**
     * 获取所有处理的对象
     *
     * @return 包含所有处理对象的列表
     */
    List<O> getAll();

    /**
     * 获取单个处理的对象
     *
     * @return 单个处理对象
     */
    O get();
}