// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.parameter;

import org.fitframework.http.annotation.RequestBean;
import org.fitframework.http.server.handler.PropertyValueMetadata;
import org.fitframework.http.server.handler.PropertyValueMetadataResolver;
import org.fitframework.ioc.annotation.AnnotationMetadata;
import org.fitframework.ioc.annotation.AnnotationMetadataResolver;
import org.fitframework.util.ReflectionUtils;
import org.fitframework.value.PropertyValue;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 表示解析带有 {@link RequestBean} 注解的参数的 {@link PropertyValueMetadataResolver}。
 *
 * @author 邬涨财
 * @since 2023-11-15
 */
public class RequestBeanMetadataResolver extends AbstractPropertyValueMetadataResolver {
    private PropertyValueMetadataResolver resolver;

    public RequestBeanMetadataResolver(AnnotationMetadataResolver annotationResolver) {
        super(annotationResolver);
    }

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return RequestBean.class;
    }

    public void setResolver(PropertyValueMetadataResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    protected List<PropertyValueMetadata> resolve(PropertyValue propertyValue, AnnotationMetadata annotations) {
        Field[] fields = ReflectionUtils.getDeclaredFields(propertyValue.getType());
        return Arrays.stream(fields)
                .map(PropertyValue::createFieldValue)
                .flatMap(fieldValue -> this.resolver.resolve(fieldValue).stream())
                .collect(Collectors.toList());
    }
}
