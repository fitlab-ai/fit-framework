// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.parameter;

import org.fitframework.http.annotation.RequestParam;
import org.fitframework.http.annotation.RequestQuery;
import org.fitframework.http.server.handler.PropertyValueMapperResolver;
import org.fitframework.http.server.handler.SourceFetcher;
import org.fitframework.http.server.handler.support.ParamValue;
import org.fitframework.http.server.handler.support.QueryFetcher;
import org.fitframework.ioc.annotation.AnnotationMetadataResolver;

import java.lang.annotation.Annotation;

/**
 * 表示解析带有 {@link RequestQuery} 注解的参数的 {@link PropertyValueMapperResolver}。
 *
 * @author 邬涨财
 * @author 季聿阶
 * @since 2023-11-20
 */
public class RequestQueryMapperResolver extends AbstractRequestParamMapperResolver {
    /**
     * 通过注解解析器来实例化 {@link RequestQueryMapperResolver}。
     *
     * @param annotationResolver 表示注解解析器的 {@link AnnotationMetadataResolver}。
     * @throws IllegalArgumentException 当 {@code annotationResolver} 为 {@code null} 时。
     */
    public RequestQueryMapperResolver(AnnotationMetadataResolver annotationResolver) {
        super(annotationResolver);
    }

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return RequestQuery.class;
    }

    @Override
    protected SourceFetcher createSourceFetcher(RequestParam requestParam) {
        return new QueryFetcher(ParamValue.custom()
                .name(requestParam.name())
                .required(requestParam.required())
                .defaultValue(requestParam.defaultValue())
                .in(requestParam.in())
                .build());
    }
}
