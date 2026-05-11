// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.parameter;

import org.fitframework.http.annotation.RequestCookie;
import org.fitframework.http.annotation.RequestParam;
import org.fitframework.http.server.handler.PropertyValueMapperResolver;
import org.fitframework.http.server.handler.SourceFetcher;
import org.fitframework.http.server.handler.support.CookieFetcher;
import org.fitframework.http.server.handler.support.ParamValue;
import org.fitframework.ioc.annotation.AnnotationMetadataResolver;
import org.fitframework.value.PropertyValue;

import java.lang.annotation.Annotation;

/**
 * 表示解析带有 {@link RequestCookie} 注解的属性值的 {@link PropertyValueMapperResolver}。
 *
 * @author 季聿阶
 * @since 2022-08-30
 */
public class RequestCookieMapperResolver extends AbstractRequestParamMapperResolver {
    /**
     * 通过注解解析器来实例化 {@link RequestCookieMapperResolver}。
     *
     * @param annotationResolver 表示注解解析器的 {@link AnnotationMetadataResolver}。
     * @throws IllegalArgumentException 当 {@code annotationResolver} 为 {@code null} 时。
     */
    public RequestCookieMapperResolver(AnnotationMetadataResolver annotationResolver) {
        super(annotationResolver);
    }

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return RequestCookie.class;
    }

    @Override
    protected boolean isArray(PropertyValue propertyValue) {
        return false;
    }

    @Override
    protected SourceFetcher createSourceFetcher(RequestParam requestParam) {
        return new CookieFetcher(ParamValue.custom()
                .name(requestParam.name())
                .in(requestParam.in())
                .defaultValue(requestParam.defaultValue())
                .required(requestParam.required())
                .build());
    }
}
