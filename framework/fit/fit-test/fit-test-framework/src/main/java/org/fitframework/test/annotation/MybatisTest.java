// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.test.annotation;

import org.fitframework.annotation.Forward;
import org.fitframework.test.domain.db.DatabaseModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于 Mybatis 测试。
 *
 * @author 易文渊
 * @since 2024-07-21
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@FitTestWithJunit
@EnableMybatis
@EnableDataSource
public @interface MybatisTest {
    /**
     * 需要注入到容器的组件类型数组。
     *
     * @return 表示需要注入到容器的组件类型数组的 {@link Class}{@code <?>[]}。
     */
    @Forward(annotation = FitTestWithJunit.class, property = "includeClasses") Class<?>[] classes() default {};

    /**
     * 获取测试数据源兼容模式。
     *
     * @return 表示数据源兼容模式的 {@link DatabaseModel}。
     * @see EnableDataSource#model()
     */
    @Forward(annotation = EnableDataSource.class, property = "model") DatabaseModel model() default DatabaseModel.NONE;
}
