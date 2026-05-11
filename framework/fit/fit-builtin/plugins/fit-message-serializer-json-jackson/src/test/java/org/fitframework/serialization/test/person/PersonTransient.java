// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.serialization.test.person;

import lombok.Data;

/**
 * 为忽略字段提供测试支持。
 *
 * @author 易文渊
 * @since 2024-10-10
 */
@Data
public class PersonTransient {
    private String name;
    private transient int age;
}