// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.parameter;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.server.handler.PropertyValueMapper;
import org.fitframework.http.server.handler.PropertyValueMapperResolver;
import org.fitframework.ioc.annotation.AnnotationMetadata;
import org.fitframework.ioc.annotation.AnnotationMetadataResolver;
import org.fitframework.value.PropertyValue;

import java.lang.annotation.Annotation;
import java.util.Optional;

/**
 * 表示 {@link PropertyValueMapperResolver} 的抽象的 Http 值映射解析器。
 *
 * @author 季聿阶
 * @since 2022-08-29
 */
public abstract class AbstractPropertyValueMapperResolver implements PropertyValueMapperResolver {
    private final AnnotationMetadataResolver annotationResolver;

    /**
     * 通过注解解析器来实例化 {@link AbstractPropertyValueMapperResolver}。
     *
     * @param annotationResolver 表示注解解析器的 {@link AnnotationMetadataResolver}。
     * @throws IllegalArgumentException 当 {@code annotationResolver} 为 {@code null} 时。
     */
    protected AbstractPropertyValueMapperResolver(AnnotationMetadataResolver annotationResolver) {
        this.annotationResolver = notNull(annotationResolver, "The annotation resolver cannot be null.");
    }

    @Override
    public Optional<PropertyValueMapper> resolve(PropertyValue propertyValue) {
        return notNull(propertyValue, "The property value cannot be null.").getElement()
                .map(this.annotationResolver::resolve)
                .filter(annotations -> annotations.isAnnotationPresent(this.getAnnotation()))
                .flatMap(annotations -> this.resolve(propertyValue, annotations));
    }

    /**
     * 获取需要解析的注解的类型。
     *
     * @return 表示需要解析的注解类型的 {@link Class}{@code <? extends }{@link Annotation}{@code >}
     */
    protected abstract Class<? extends Annotation> getAnnotation();

    /**
     * 解析参数及注解，来获取属性值映射器。
     *
     * @param propertyValue 表示待解析的属性值的 {@link PropertyValue}。
     * @param annotations 表示待解析的注解的 {@link AnnotationMetadata}。
     * @return 表示解析后的 Http 值映射器和元数据的元组的 {@link Optional}{@code <}{@link PropertyValueMapper}{@code >}。
     */
    protected abstract Optional<PropertyValueMapper> resolve(PropertyValue propertyValue,
            AnnotationMetadata annotations);
}
