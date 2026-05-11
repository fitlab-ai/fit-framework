// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.example;

import org.fitframework.annotation.Component;
import org.fitframework.annotation.Fitable;

/**
 * 表示 {@link Weather} 的默认实现。
 *
 * @author 董智豪
 * @since 2025-06-21
 */
@Component
public class DefaultWeather implements Weather {
    @Override
    @Fitable(id = "default-weather")
    public String get() {
        return "Default weather application is working.";
    }
}
