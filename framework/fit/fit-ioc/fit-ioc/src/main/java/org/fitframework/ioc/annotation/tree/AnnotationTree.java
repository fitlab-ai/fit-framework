// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.annotation.tree;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * 为注解提供树形结构定义。
 *
 * @author 梁济时
 * @since 2022-07-07
 */
public interface AnnotationTree extends AnnotationTreeNodeContainer, ConverterCache {
    /**
     * 使用注解树的信息生成注解信息。
     *
     * @return 表示注解信息列表的 {@link List}{@code <}{@link Annotation}{@code >}。
     */
    List<Annotation> toAnnotations();
}
