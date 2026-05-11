// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.parameter;

import org.fitframework.http.annotation.PathVariable;
import org.fitframework.http.annotation.RequestParam;
import org.fitframework.http.server.handler.PropertyValueMapperResolver;
import org.fitframework.http.server.handler.SourceFetcher;
import org.fitframework.http.server.handler.support.ParamValue;
import org.fitframework.http.server.handler.support.PathVariableFetcher;
import org.fitframework.ioc.annotation.AnnotationMetadataResolver;
import org.fitframework.value.PropertyValue;

import java.lang.annotation.Annotation;

/**
 * 表示解析带有 {@link PathVariable} 注解的参数的 {@link PropertyValueMapperResolver}。
 *
 * @author 季聿阶
 * @since 2022-08-29
 */
public class PathVariableMapperResolver extends AbstractRequestParamMapperResolver {
    /**
     * 通过注解解析器来实例化 {@link PathVariableMapperResolver}。
     *
     * @param annotationResolver 表示注解解析器的 {@link AnnotationMetadataResolver}。
     * @throws IllegalArgumentException 当 {@code annotationResolver} 为 {@code null} 时。
     */
    public PathVariableMapperResolver(AnnotationMetadataResolver annotationResolver) {
        super(annotationResolver);
    }

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return PathVariable.class;
    }

    @Override
    protected boolean isArray(PropertyValue propertyValue) {
        return false;
    }

    @Override
    protected SourceFetcher createSourceFetcher(RequestParam requestParam) {
        return new PathVariableFetcher(ParamValue.custom()
                .name(requestParam.name())
                .in(requestParam.in())
                .defaultValue(requestParam.defaultValue())
                .required(requestParam.required())
                .build());
    }
}
