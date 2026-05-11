// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.embed;

import java.util.List;

/**
 * 表示生成嵌入的实体。
 *
 * @author 易文渊
 * @since 2024-04-13
 */
public interface Embedding {
    /**
     * 获取嵌入向量。
     *
     * @return 表示嵌入向量的 {@link List}{@code <}{@link Float}{@code >}。
     */
    List<Float> embedding();
}