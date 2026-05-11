// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.example.controller;

import org.fitframework.example.service.MyService;
import org.fitframework.http.annotation.GetMapping;
import org.fitframework.annotation.Component;

/**
 * HTTP 控制器。
 */
@Component
public class MyController {
    private final MyService myService;

    public MyController(MyService myService) {
        this.myService = myService;
    }

    @GetMapping(path = "/hello")
    public void hello() {
        this.myService.doSomething();
    }
}
