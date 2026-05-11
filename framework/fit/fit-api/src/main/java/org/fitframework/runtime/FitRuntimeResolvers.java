// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.runtime;

import org.fitframework.ioc.BeanResolver;
import org.fitframework.ioc.DependencyResolver;
import org.fitframework.ioc.annotation.AnnotationMetadataResolver;

/**
 * 定义 {@link FitRuntime} 中所使用的所有解析程序。
 *
 * @author 梁济时
 * @since 2022-12-05
 */
public interface FitRuntimeResolvers {
    /**
     * 获取 Bean 解析程序。
     *
     * @return 表示 Bean 解析程序的 {@link BeanResolver}。
     */
    BeanResolver bean();

    /**
     * 获取依赖解析程序。
     *
     * @return 表示依赖解析程序的 {@link DependencyResolver}。
     */
    DependencyResolver dependency();

    /**
     * 获取注解解析程序。
     *
     * @return 表示注解解析程序的 {@link AnnotationMetadataResolver}。
     */
    AnnotationMetadataResolver annotation();
}
