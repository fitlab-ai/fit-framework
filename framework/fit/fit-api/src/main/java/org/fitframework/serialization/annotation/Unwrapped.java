// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.serialization.annotation;

import org.fitframework.util.StringUtils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记属性是否展开。
 *
 * @author 易文渊
 * @since 2024-8-17
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Unwrapped {
    /**
     * 获取前缀。
     *
     * @return 表示前缀的 {@link String}。
     */
    String prefix() default StringUtils.EMPTY;

    /**
     * 获取后缀。
     *
     * @return 表示后缀的 {@link String}。
     */
    String suffix() default StringUtils.EMPTY;
}
