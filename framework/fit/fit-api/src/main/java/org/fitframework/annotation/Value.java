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
 * 用于注入配置信息。
 *
 * @author 季聿阶
 * @since 2020-07-28
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
public @interface Value {
    /**
     * 记录待注入的变量值，用于参数替换。
     *
     * @return 表示待注入的变量值的 {@link String}。
     */
    String value() default "";

    /**
     * 配置信息所在的配置路径。
     *
     * @return 表示配置路径的 {@link String}。
     */
    String configPath() default "";
}
