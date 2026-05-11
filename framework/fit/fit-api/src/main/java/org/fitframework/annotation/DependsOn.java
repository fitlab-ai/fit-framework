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
 * 声明 Bean 的依赖。
 *
 * @author 梁济时
 * @since 2022-11-29
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DependsOn {
    /**
     * 指示所依赖的 Bean 的名称。
     *
     * @return 表示所依赖 Bean 的名称的 {@link String}{@code []}。
     */
    String[] value() default {};
}
