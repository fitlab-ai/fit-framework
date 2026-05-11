// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.annotation;

import org.fitframework.annotation.Scope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于表示类的切面声明。
 *
 * @author 郭龙飞
 * @since 2023-03-07
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface Aspect {
    /**
     * 获取切面的生效范围。
     *
     * @return 表示切面的生效范围的 {@link Scope}。
     */
    Scope scope() default Scope.PLUGIN;
}
