// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.example.ai.chat.agent;

import org.fitframework.annotation.Component;
import org.fitframework.runtime.FitStarter;

/**
 * 启动程序。
 *
 * @author 易文渊
 * @since 2024-08-29
 */
@Component
public class DemoApplication {
    public static void main(String[] args) {
        FitStarter.start(DemoApplication.class, args);
    }
}