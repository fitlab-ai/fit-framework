// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.example.controller;

import org.fitframework.example.domain.User;
import org.fitframework.http.annotation.GetMapping;
import org.fitframework.http.annotation.RequestParam;
import org.fitframework.annotation.Component;

/**
 * 表示用户资源的控制器。
 *
 * @author 季聿阶
 * @since 2025-01-31
 */
@Component
public class UserController {
    private static int counter = 0;

    @GetMapping(path = "/user")
    public User getUser(@RequestParam("name") String name, @RequestParam("age") String age) {
        return new User(name, age, ++counter);
    }
}
