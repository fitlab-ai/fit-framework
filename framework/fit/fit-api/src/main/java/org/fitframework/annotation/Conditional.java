// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.annotation;

import org.fitframework.ioc.Condition;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 为 Bean 的加载提供条件。
 *
 * @author 梁济时
 * @since 2022-11-14
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Conditional {
    /**
     * 获取 Bean 生效条件的类型。
     *
     * @return 表示 Bean 生效条件类型的 {@link Class}。
     */
    Class<? extends Condition>[] value() default {};
}
