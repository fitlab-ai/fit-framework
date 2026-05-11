// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.example.controller;

import org.fitframework.example.Weather;
import org.fitframework.http.annotation.GetMapping;
import org.fitframework.annotation.Component;
import org.fitframework.annotation.Fit;

/**
 * 表示助手的控制器。
 *
 * @author 董智豪
 * @since 2025-06-21
 */
@Component
public class AssistantController {
    private final Weather weather;

    public AssistantController(@Fit Weather weather) {
        this.weather = weather;
    }

    /**
     * 获取天气信息。
     *
     * @return 表示天气信息的 {@link String}。
     */
    @GetMapping(path = "/weather")
    public String getWeather() {
        return this.weather.get();
    }
}
