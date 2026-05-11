// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.example.service.impl;

import org.fitframework.example.service.MyService;
import org.fitframework.annotation.Component;

/**
 * 服务接口的实现。
 */
@Component
public class MyServiceImpl implements MyService {
    @Override
    public void doSomething() {
        System.out.println("do something");
    }
}
