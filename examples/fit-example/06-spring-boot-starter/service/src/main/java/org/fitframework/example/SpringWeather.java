// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.example;

import org.fitframework.annotation.Genericable;

/**
 * 表示 Spring 应用需要实现的接口服务。
 *
 * @author 季聿阶
 * @since 2025-02-13
 */
public interface SpringWeather {
    /**
     * 获取天气信息。
     *
     * @return 表示天气信息的 {@link String}。
     */
    @Genericable(id = "SpringWeather")
    String get();
}
