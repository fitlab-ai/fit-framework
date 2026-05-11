// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 容器注解，用于支持多个 {@link RequestAuth} 注解的组合使用。
 *
 * @author 季聿阶
 * @since 2025-09-30
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
public @interface RequestAuths {
    /**
     * 多个鉴权配置。
     *
     * @return 表示多个鉴权配置的 {@link RequestAuth} 数组。
     */
    RequestAuth[] value();
}