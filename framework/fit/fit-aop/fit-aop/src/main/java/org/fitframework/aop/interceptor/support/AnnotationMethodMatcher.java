// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.aop.interceptor.MethodMatcher;
import org.fitframework.inspection.Nonnull;
import org.fitframework.ioc.annotation.AnnotationMetadata;
import org.fitframework.ioc.annotation.AnnotationMetadataResolver;
import org.fitframework.ioc.annotation.AnnotationMetadataResolvers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 表示携带指定注解的匹配器。
 *
 * @author 季聿阶
 * @since 2022-12-14
 */
public class AnnotationMethodMatcher implements MethodMatcher {
    private final AnnotationMetadataResolver resolver;
    private final Class<? extends Annotation> annotationClass;

    /**
     * 使用指定的注解类型初始化 {@link AnnotationMethodMatcher} 的新实例。
     *
     * @param annotationClass 表示注解类型的 {@link Class}{@code <? extends }{@link Annotation}{@code >}。
     * @throws IllegalArgumentException 当 {@code annotationClass} 为 {@code null} 时。
     */
    public AnnotationMethodMatcher(Class<? extends Annotation> annotationClass) {
        this.resolver = AnnotationMetadataResolvers.create();
        this.annotationClass = notNull(annotationClass, "The annotation class cannot be null.");
    }

    @Override
    public MatchResult match(@Nonnull Method method) {
        AnnotationMetadata annotations = this.resolver.resolve(method);
        return MatchResult.match(annotations.isAnnotationPresent(this.annotationClass));
    }
}
