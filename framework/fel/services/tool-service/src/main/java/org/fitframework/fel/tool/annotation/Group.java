// SPDX-License-Identifier: MIT
// Copyright (c) 2025-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.tool.annotation;

import org.fitframework.util.StringUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于组的定义。
 *
 * @author 曹嘉美
 * @since 2024-10-25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Group {
    /**
     * 获取组的名。
     *
     * @return 表示组名的 {@link String}。
     */
    String name();

    /**
     * 获取组的摘要。
     *
     * @return 表示组摘要的 {@link String}。
     */
    String summary() default StringUtils.EMPTY;

    /**
     * 获取组的描述。
     *
     * @return 表示组描述的 {@link String}。
     */
    String description() default StringUtils.EMPTY;

    /**
     * 获取工具方法的扩展属性。
     *
     * @return 表示工具方法扩展属性的 {@link Attribute}{@code []}。
     */
    Attribute[] extensions() default {};
}
