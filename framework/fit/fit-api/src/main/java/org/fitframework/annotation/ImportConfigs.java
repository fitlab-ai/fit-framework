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
 * 指示需要导入配置。
 *
 * @author 梁济时
 * @since 2023-01-05
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ImportConfigs {
    /**
     * 指示待导入的配置的资源路径。
     *
     * @return 表示配置的资源路径的 {@link String}{@code []}。
     */
    String[] value() default {};
}
