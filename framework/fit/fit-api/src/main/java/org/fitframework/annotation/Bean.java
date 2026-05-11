// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.annotation;

import org.fitframework.util.StringUtils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于表示 Bean。
 *
 * @author 季聿阶
 * @since 2023-02-24
 */
@Documented
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Bean {
    /**
     * 获取 Bean 的名字。
     *
     * @return 标识 Bean 的名字的 {@link String}。
     */
    @Forward(annotation = Component.class, property = "name") String value() default StringUtils.EMPTY;
}
