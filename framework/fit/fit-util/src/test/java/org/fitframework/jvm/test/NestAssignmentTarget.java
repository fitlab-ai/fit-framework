// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.jvm.test;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * jvm 模块测试文件
 *
 * @author 郭龙飞
 * @since 2023-01-04
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NestAssignmentTarget {
    int integerValue() default 0;
}
