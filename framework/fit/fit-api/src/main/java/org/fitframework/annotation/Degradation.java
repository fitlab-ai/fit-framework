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
 * 为 {@link Fitable} 提供降级的默认定义。
 *
 * @author 季聿阶
 * @see Fitable
 * @since 2022-06-09
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Degradation {
    /**
     * 获取降级后的实现的别名。
     *
     * @return 表示降级后的实现的别名的 {@link String}。
     */
    String to();
}
