// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.example;

import org.fitframework.annotation.Component;
import org.fitframework.annotation.ScanPackages;
import org.fitframework.runtime.FitStarter;

/**
 * 启动类。
 *
 * @author 董智豪
 * @since 2025-06-21
 */
@Component
@ScanPackages("com.example")
public class AssistantStarter {
    public static void main(String[] args) {
        FitStarter.start(AssistantStarter.class, args);
    }
}
