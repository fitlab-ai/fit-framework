// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.test.annotation;

import org.fitframework.test.domain.db.DatabaseModel;
import org.fitframework.test.domain.listener.DataSourceListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于测试框架注入数据源。
 *
 * @author 易文渊
 * @see DataSourceListener
 * @since 2024-07-21
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableDataSource {
    /**
     * 获取测试数据源兼容模式。
     *
     * @return 表示数据源兼容模式的 {@link DatabaseModel}。
     */
    DatabaseModel model() default DatabaseModel.NONE;
}
