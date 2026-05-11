// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.annotation.convert;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示原始注解，保存整数值。
 *
 * @author 梁济时
 * @since 2023-01-28
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface IntValue {
    /**
     * 表示注解的值。
     *
     * @return 表示注解值的 32 位整数。
     */
    int value() default 0;
}
