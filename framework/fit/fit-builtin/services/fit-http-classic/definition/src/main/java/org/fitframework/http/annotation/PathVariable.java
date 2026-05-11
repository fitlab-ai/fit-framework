// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.annotation;

import org.fitframework.http.server.handler.Source;
import org.fitframework.annotation.Forward;
import org.fitframework.util.StringUtils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示 REST 接口的请求映射中的路径参数。
 *
 * @author 季聿阶
 * @since 2023-01-09
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@RequestParam(in = Source.PATH)
public @interface PathVariable {
    /**
     * 获取路径参数的名字。
     *
     * @return 表示路径参数名字的 {@link String}。
     * @see #name()
     */
    @Forward(annotation = PathVariable.class, property = "name") String value() default StringUtils.EMPTY;

    /**
     * 获取路径参数的名字。
     *
     * @return 表示路径参数名字的 {@link String}。
     */
    @Forward(annotation = RequestParam.class, property = "name") String name() default StringUtils.EMPTY;
}
