// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.example;

import org.fitframework.another.AnotherPluginWeather;
import org.fitframework.annotation.Component;
import org.fitframework.annotation.Fitable;

/**
 * 表示 {@link AnotherPluginWeather} 的默认实现。
 *
 * @author 杭潇
 * @since 2025-02-27
 */
@Component
public class AnotherFitWeather implements AnotherPluginWeather {
    @Override
    @Fitable(id = "default")
    public String get(String city) {
        return "Sunny";
    }
}
