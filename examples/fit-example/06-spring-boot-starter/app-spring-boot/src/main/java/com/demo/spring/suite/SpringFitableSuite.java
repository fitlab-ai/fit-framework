// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package com.demo.spring.suite;

import org.fitframework.example.SpringWeather;
import org.fitframework.annotation.Fitable;
import org.fitframework.annotation.FitableSuite;

import org.springframework.stereotype.Component;

/**
 * 表示 Spring Boot 底座上的 Fitables 实现。
 *
 * @author 季聿阶
 * @since 2025-02-13
 */
@Component
@FitableSuite
public class SpringFitableSuite implements SpringWeather {
    @Override
    @Fitable(id = "default")
    public String get() {
        return "Spring weather service is working.";
    }
}
