// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.aspect.test;

import org.fitframework.annotation.Forward;
import org.fitframework.aop.annotation.After;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 *
 * @author 白鹏坤
 * @since 2023-04-04
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@After
public @interface After1 {
    @Forward(annotation = After.class, property = "value") String value() default "";

    @Forward(annotation = After.class, property = "argNames") String argNames() default "";
}
