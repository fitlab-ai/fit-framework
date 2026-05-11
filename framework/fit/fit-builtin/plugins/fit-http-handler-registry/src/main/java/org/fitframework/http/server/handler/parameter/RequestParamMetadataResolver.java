// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.parameter;

import org.fitframework.http.annotation.RequestParam;
import org.fitframework.http.server.handler.PropertyValueMetadata;
import org.fitframework.http.server.handler.PropertyValueMetadataResolver;
import org.fitframework.annotation.Property;
import org.fitframework.ioc.annotation.AnnotationMetadata;
import org.fitframework.ioc.annotation.AnnotationMetadataResolver;
import org.fitframework.util.StringUtils;
import org.fitframework.value.PropertyValue;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

/**
 * 表示解析带有 {@link RequestParam} 注解的参数的 {@link PropertyValueMetadataResolver}。
 *
 * @author 季聿阶
 * @since 2023-01-12
 */
public class RequestParamMetadataResolver extends AbstractPropertyValueMetadataResolver {
    public RequestParamMetadataResolver(AnnotationMetadataResolver annotationResolver) {
        super(annotationResolver);
    }

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return RequestParam.class;
    }

    @Override
    protected List<PropertyValueMetadata> resolve(PropertyValue propertyValue, AnnotationMetadata annotations) {
        RequestParam param = annotations.getAnnotation(RequestParam.class);
        Property property = annotations.getAnnotation(Property.class);
        PropertyValueMetadata propertyValueMetadata = PropertyValueMetadata.builder()
                .name(StringUtils.blankIf(param.name(), propertyValue.getName()))
                .in(param.in())
                .description(property != null ? property.description() : StringUtils.EMPTY)
                .example(property != null ? property.example() : StringUtils.EMPTY)
                .type(propertyValue.getParameterizedType())
                .isRequired(param.required())
                .defaultValue(param.defaultValue())
                .element(propertyValue.getElement().orElse(null))
                .build();
        return Collections.singletonList(propertyValueMetadata);
    }
}
