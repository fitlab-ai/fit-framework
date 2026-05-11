// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.annotation;

import java.lang.annotation.Annotation;

/**
 * 为注解提供排除程序。
 *
 * @author 梁济时
 * @since 2022-05-18
 */
@FunctionalInterface
public interface AnnotationEliminator {
    /**
     * 尝试排除指定注解。
     *
     * @param annotation 表示待排除的注解的 {@link Annotation}。
     * @return 若需要排除该注解，则为 {@code true}；否则为 {@code false}。
     */
    boolean eliminate(Annotation annotation);
}
