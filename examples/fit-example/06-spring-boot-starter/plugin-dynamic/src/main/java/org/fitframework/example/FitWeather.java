// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.example;

import org.fitframework.annotation.Component;
import org.fitframework.annotation.Fitable;

import java.util.Objects;

/**
 * 表示 {@link PluginWeather} 的默认实现。
 *
 * @author 季聿阶
 * @since 2025-02-13
 */
@Component
public class FitWeather implements PluginWeather {
    private final SpringWeather springWeather;

    public FitWeather(SpringWeather springWeather) {
        this.springWeather = springWeather;
    }

    @Override
    @Fitable(id = "default")
    public String get(String location) {
        if (Objects.equals(location, "fit")) {
            return "FIT weather plugin is working.";
        }
        return this.springWeather.get();
    }
}
