// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 为 {@link Alias} 提供可重复的定义。
 *
 * @author 梁济时
 * @since 2022-05-30
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface Aliases {
    /**
     * 获取定义的多个 {@link Alias}。
     *
     * @return 表示定义的所有 {@link Alias} 的数组。
     */
    Alias[] value();
}
