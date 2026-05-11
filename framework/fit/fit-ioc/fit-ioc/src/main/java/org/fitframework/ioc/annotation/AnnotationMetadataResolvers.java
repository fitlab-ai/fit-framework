// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.annotation;

import org.fitframework.ioc.annotation.support.DefaultAnnotationMetadataResolver;

/**
 * 为 {@link AnnotationMetadataResolver} 提供工具方法。
 *
 * @author 季聿阶
 * @since 2023-03-25
 */
public final class AnnotationMetadataResolvers {
    private static final AnnotationMetadataResolver INSTANCE = new DefaultAnnotationMetadataResolver();

    private AnnotationMetadataResolvers() {}

    /**
     * 获取一个注解解析器的实例。
     *
     * @return 表示注解解析器的实例的 {@link AnnotationMetadataResolver}。
     */
    public static AnnotationMetadataResolver create() {
        return INSTANCE;
    }
}
