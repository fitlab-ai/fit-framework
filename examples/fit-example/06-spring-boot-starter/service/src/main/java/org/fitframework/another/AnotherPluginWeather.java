// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.another;

import org.fitframework.annotation.Genericable;

/**
 * 表示 FIT 插件需要实现的接口服务。
 *
 * @author 杭潇
 * @since 2025-02-27
 */
public interface AnotherPluginWeather {
    /**
     * 获取天气信息。
     *
     * @param city 表示城市的 {@link String}。
     * @return 表示天气信息的 {@link String}。
     */
    @Genericable(id = "TodayWeather")
    String get(String city);
}
