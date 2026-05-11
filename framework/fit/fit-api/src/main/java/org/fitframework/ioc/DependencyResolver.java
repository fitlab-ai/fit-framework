// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc;

import org.fitframework.inspection.Nonnull;
import org.fitframework.ioc.annotation.AnnotationMetadata;

import java.lang.reflect.Type;

/**
 * 为 Bean 依赖提供解析程序。
 *
 * @author 梁济时
 * @since 2022-05-10
 */
public interface DependencyResolver {
    /**
     * 解析依赖。
     *
     * @param source 表示待解析依赖所属的 Bean 的元数据的 {@link BeanMetadata}。
     * @param name 表示所依赖的 Bean 的名称的 {@link String}。
     * @param type 表示所依赖的 Bean 的类型的 {@link Type}。
     * @param annotations 表示所依赖的 Bean 所在位置的注解元数据的 {@link AnnotationMetadata}。
     * @return 表示依赖的解析结果的 {@link DependencyResolvingResult}。
     */
    DependencyResolvingResult resolve(@Nonnull BeanMetadata source, String name, @Nonnull Type type,
            @Nonnull AnnotationMetadata annotations);
}
