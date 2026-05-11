// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc;

import org.fitframework.ioc.annotation.AnnotationMetadata;

/**
 * 为 Bean 提供生效条件。
 *
 * @author 梁济时
 * @since 2022-11-14
 */
public interface Condition {
    /**
     * 检查条件是否匹配。
     *
     * @param container 表示 Bean 容器的 {@link BeanContainer}。
     * @param annotations 表示需要匹配的元数据定义的 {@link AnnotationMetadata}。
     * @return 表示是否匹配的 {@code boolean}。
     */
    boolean match(BeanContainer container, AnnotationMetadata annotations);
}
