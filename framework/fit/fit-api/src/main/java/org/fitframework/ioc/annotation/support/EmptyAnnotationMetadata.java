// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.annotation.support;

import static org.fitframework.util.ObjectUtils.cast;

import org.fitframework.ioc.annotation.AnnotationMetadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;

/**
 * 为 {@link AnnotationMetadata} 提供空实现。
 *
 * @author 梁济时
 * @since 2022-08-16
 */
public class EmptyAnnotationMetadata implements AnnotationMetadata {
    /**
     * 获取 {@link EmptyAnnotationMetadata} 的唯一实例。
     */
    public static final EmptyAnnotationMetadata INSTANCE = new EmptyAnnotationMetadata();

    /**
     * 隐藏默认构造方法，避免单例类被外部实例化。
     */
    private EmptyAnnotationMetadata() {}

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> type) {
        return false;
    }

    @Override
    public Annotation[] getAnnotations() {
        return new Annotation[0];
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> type) {
        return null;
    }

    @Override
    public <T extends Annotation> T[] getAnnotationsByType(Class<T> type) {
        return cast(Array.newInstance(type, 0));
    }
}
