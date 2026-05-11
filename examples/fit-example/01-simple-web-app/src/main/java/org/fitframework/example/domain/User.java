// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.example.domain;

/**
 * 表示用户资源类。
 *
 * @author 季聿阶
 * @since 2025-01-31
 */
public class User {
    private final String name;
    private final String age;
    private final int id;

    public User(String name, String age, int id) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public String getAge() {
        return this.age;
    }

    public int getId() {
        return id;
    }
}
