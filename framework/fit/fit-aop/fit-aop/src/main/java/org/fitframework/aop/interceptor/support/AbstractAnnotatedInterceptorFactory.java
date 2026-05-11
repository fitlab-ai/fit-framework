// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.aop.interceptor.MethodInterceptorFactory;
import org.fitframework.inspection.Nonnull;
import org.fitframework.ioc.annotation.AnnotationMetadata;
import org.fitframework.ioc.annotation.AnnotationMetadataResolvers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 表示通过方法注解来生成方法拦截器的工厂。
 *
 * @author 季聿阶
 * @since 2022-12-14
 */
public abstract class AbstractAnnotatedInterceptorFactory implements MethodInterceptorFactory {
    private final Class<? extends Annotation> annotationClass;

    /**
     * 使用指定的注解类型初始化 {@link AbstractAnnotatedInterceptorFactory} 的新实例。
     *
     * @param annotationClass 表示注解类型的 {@link Class}{@code <? extends }{@link Annotation}{@code >}。
     * @throws IllegalArgumentException 当 {@code annotationClass} 为 {@code null} 时。
     */
    protected AbstractAnnotatedInterceptorFactory(Class<? extends Annotation> annotationClass) {
        this.annotationClass = notNull(annotationClass, "The annotation class cannot be null.");
    }

    @Override
    public boolean isInterceptMethod(@Nonnull Method method) {
        return this.getAnnotations(method).isAnnotationPresent(this.annotationClass);
    }

    /**
     * 获取指定方法的注解元数据信息。
     *
     * @param method 表示指定方法的 {@link Method}。
     * @return 表示指定方法的注解元数据信息的 {@link AnnotationMetadata}。
     */
    protected AnnotationMetadata getAnnotations(@Nonnull Method method) {
        return AnnotationMetadataResolvers.create().resolve(method);
    }
}
