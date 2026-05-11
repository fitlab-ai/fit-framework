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
 * 定义需要扫描的包。
 *
 * @author 梁济时
 * @since 2022-08-08
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ScanPackages {
    /**
     * 指示需要扫描的包。
     *
     * @return 表示待扫描的包的 {@link String}{@code []}。
     */
    String[] value() default {};
}
