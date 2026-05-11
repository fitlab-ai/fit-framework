// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.annotation;

import org.fitframework.ioc.BeanApplicableScope;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 为 {@link Fitable} 定义可用范围。
 *
 * @author 梁济时
 * @since 2022-08-30
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface ApplicableScope {
    /**
     * 指示 {@link Fitable} 的可用范围。默认为 {@link BeanApplicableScope#INSENSITIVE}。
     *
     * @return 表示可用范围的 {@link String}。
     */
    BeanApplicableScope value() default BeanApplicableScope.INSENSITIVE;
}
