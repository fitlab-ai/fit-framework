// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.example;

import org.fitframework.annotation.Genericable;

/**
 * 表示通用接口服务。
 *
 * @author 董智豪
 * @since 2025-06-21
 */
public interface Weather {
    /**
     * 获取天气信息。
     *
     * @return 表示天气信息的 {@link String}。
     */
    @Genericable(id = "Weather")
    String get();
}
