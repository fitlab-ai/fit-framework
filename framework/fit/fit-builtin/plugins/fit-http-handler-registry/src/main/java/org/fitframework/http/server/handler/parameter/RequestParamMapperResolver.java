// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.parameter;

import org.fitframework.http.annotation.RequestParam;
import org.fitframework.http.server.handler.PropertyValueMapperResolver;
import org.fitframework.http.server.handler.Source;
import org.fitframework.http.server.handler.SourceFetcher;
import org.fitframework.http.server.handler.support.CookieFetcher;
import org.fitframework.http.server.handler.support.FormUrlEncodedEntityFetcher;
import org.fitframework.http.server.handler.support.HeaderFetcher;
import org.fitframework.http.server.handler.support.ObjectEntityFetcher;
import org.fitframework.http.server.handler.support.ParamValue;
import org.fitframework.http.server.handler.support.PathVariableFetcher;
import org.fitframework.http.server.handler.support.QueryFetcher;
import org.fitframework.ioc.annotation.AnnotationMetadataResolver;
import org.fitframework.util.MapBuilder;

import java.util.Map;
import java.util.function.Function;

/**
 * 表示解析带有 {@link RequestParam} 注解的参数的 {@link PropertyValueMapperResolver}。
 *
 * @author 季聿阶
 * @since 2022-08-29
 */
public class RequestParamMapperResolver extends AbstractRequestParamMapperResolver {
    private static final Map<Source, Function<ParamValue, SourceFetcher>> SOURCE_FETCHER_MAPPING =
            MapBuilder.<Source, Function<ParamValue, SourceFetcher>>get()
                    .put(Source.QUERY, QueryFetcher::new)
                    .put(Source.HEADER, HeaderFetcher::new)
                    .put(Source.COOKIE, CookieFetcher::new)
                    .put(Source.PATH, PathVariableFetcher::new)
                    .put(Source.BODY, ObjectEntityFetcher::new)
                    .put(Source.FORM, FormUrlEncodedEntityFetcher::new)
                    .build();

    /**
     * 通过注解解析器来实例化 {@link RequestParamMapperResolver}。
     *
     * @param annotationResolver 表示注解解析器的 {@link AnnotationMetadataResolver}。
     * @throws IllegalArgumentException 当 {@code annotationResolver} 为 {@code null} 时。
     */
    public RequestParamMapperResolver(AnnotationMetadataResolver annotationResolver) {
        super(annotationResolver);
    }

    @Override
    protected SourceFetcher createSourceFetcher(RequestParam requestParam) {
        Function<ParamValue, SourceFetcher> function = SOURCE_FETCHER_MAPPING.get(requestParam.in());
        return function.apply(ParamValue.custom()
                .name(requestParam.name())
                .in(requestParam.in())
                .defaultValue(requestParam.defaultValue())
                .required(requestParam.required())
                .build());
    }
}
