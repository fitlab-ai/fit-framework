// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.annotation.repeatable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 为 {@link Value} 提供可重复的定义。
 *
 * @author 梁济时
 * @since 2022-05-31
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RepeatableValue {
    /**
     * 表示定义的可重复注解。
     *
     * @return 表示可重复注解的数组。
     */
    Value[] value();
}
