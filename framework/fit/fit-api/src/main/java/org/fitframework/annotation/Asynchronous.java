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
 * 异步执行的标记。
 *
 * @author 季聿阶
 * @since 2022-11-11
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Asynchronous {
    /**
     * 获取异步执行的线程池的名字。
     *
     * @return 表示异步执行的线程池的名字的 {@link String}。
     */
    @Forward(annotation = Asynchronous.class, property = "executor") String value() default "";

    /**
     * 获取异步执行的线程池的名字。
     *
     * @return 表示异步执行的线程池的名字的 {@link String}。
     */
    String executor() default "";
}
