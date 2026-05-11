// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.serialization.test.enums;

import org.fitframework.annotation.Property;

/**
 * 测试用性别枚举。
 *
 * @author 易文渊
 * @since 2024-09-27
 */
public enum Gender {
    @Property(name = "man")
    MAN,
    @Property(name = "woman")
    WOMAN
}