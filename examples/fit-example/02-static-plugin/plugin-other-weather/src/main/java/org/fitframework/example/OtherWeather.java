// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.example;

import org.fitframework.annotation.Component;
import org.fitframework.annotation.Fitable;

/**
 * 表示 {@link Weather} 的另一个实现。
 *
 * @author 季聿阶
 * @since 2025-01-31
 */
@Component
public class OtherWeather implements Weather {
    @Override
    @Fitable(id = "other")
    public String get() {
        return "Other weather plugin is working.";
    }
}
