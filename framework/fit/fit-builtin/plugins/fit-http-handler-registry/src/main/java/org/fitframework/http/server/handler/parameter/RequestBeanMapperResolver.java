// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.parameter;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.annotation.PathVariable;
import org.fitframework.http.annotation.RequestBean;
import org.fitframework.http.annotation.RequestBody;
import org.fitframework.http.annotation.RequestCookie;
import org.fitframework.http.annotation.RequestForm;
import org.fitframework.http.annotation.RequestParam;
import org.fitframework.http.annotation.RequestQuery;
import org.fitframework.http.server.handler.PropertyValueMapper;
import org.fitframework.http.server.handler.PropertyValueMetadataResolver;
import org.fitframework.http.server.handler.Source;
import org.fitframework.http.server.handler.SourceFetcher;
import org.fitframework.http.server.handler.support.CookieFetcher;
import org.fitframework.http.server.handler.support.FormUrlEncodedEntityFetcher;
import org.fitframework.http.server.handler.support.HeaderFetcher;
import org.fitframework.http.server.handler.support.MultiSourcesPropertyValueMapper;
import org.fitframework.http.server.handler.support.ObjectEntityFetcher;
import org.fitframework.http.server.handler.support.ParamValue;
import org.fitframework.http.server.handler.support.PathVariableFetcher;
import org.fitframework.http.server.handler.support.QueryFetcher;
import org.fitframework.http.server.handler.support.SourceFetcherInfo;
import org.fitframework.http.server.handler.support.TypeTransformationPropertyValueMapper;
import org.fitframework.ioc.annotation.AnnotationMetadata;
import org.fitframework.ioc.annotation.AnnotationMetadataResolver;
import org.fitframework.json.schema.util.SchemaTypeUtils;
import org.fitframework.util.MapBuilder;
import org.fitframework.util.ReflectionUtils;
import org.fitframework.util.StringUtils;
import org.fitframework.value.PropertyValue;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * 表示解析带有 {@link RequestBean} 注解的参数的 {@link PropertyValueMetadataResolver}。
 *
 * @author 邬涨财
 * @since 2023-11-14
 */
public class RequestBeanMapperResolver extends AbstractPropertyValueMapperResolver {
    /**
     * <p>考虑到 PathVariable 等请求映射参数注解带有 RequestParam 注解，所以在初始化 IS_ARRAY_MAPPING 时，需要将 RequestParam
     * 注解的 put 操作放置在最后；否则其他参数注解可能不会正确的从 IS_ARRAY_MAPPING 取出。</p>
     */
    private static final Map<Class<? extends Annotation>, Function<PropertyValue, Boolean>> IS_ARRAY_MAPPING =
            MapBuilder.<Class<? extends Annotation>, Function<PropertyValue, Boolean>>get(LinkedHashMap::new)
                    .put(PathVariable.class, (propertyValue -> false))
                    .put(RequestBody.class, (propertyValue -> false))
                    .put(RequestCookie.class, (propertyValue -> false))
                    .put(RequestForm.class, (propertyValue -> List.class.isAssignableFrom(propertyValue.getType())))
                    .put(RequestQuery.class, (propertyValue -> List.class.isAssignableFrom(propertyValue.getType())))
                    .put(RequestParam.class, (propertyValue -> List.class.isAssignableFrom(propertyValue.getType())))
                    .build();
    private static final Map<Source, Function<ParamValue, SourceFetcher>> SOURCE_FETCHER_MAPPING =
            MapBuilder.<Source, Function<ParamValue, SourceFetcher>>get()
                    .put(Source.QUERY, QueryFetcher::new)
                    .put(Source.HEADER, HeaderFetcher::new)
                    .put(Source.COOKIE, CookieFetcher::new)
                    .put(Source.PATH, PathVariableFetcher::new)
                    .put(Source.BODY, ObjectEntityFetcher::new)
                    .put(Source.FORM, FormUrlEncodedEntityFetcher::new)
                    .build();
    private static final String DESTINATION_NAME_SEPARATOR = ".";

    private final AnnotationMetadataResolver annotationResolver;

    /**
     * 通过注解解析器来实例化 {@link RequestBeanMapperResolver}。
     *
     * @param annotationResolver 表示注解解析器的 {@link AnnotationMetadataResolver}。
     * @throws IllegalArgumentException 当 {@code annotationResolver} 为 {@code null} 时。
     */
    public RequestBeanMapperResolver(AnnotationMetadataResolver annotationResolver) {
        super(annotationResolver);
        this.annotationResolver = notNull(annotationResolver, "The annotation metadata resolver cannot be null.");
    }

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return RequestBean.class;
    }

    @Override
    protected Optional<PropertyValueMapper> resolve(PropertyValue propertyValue,
            AnnotationMetadata annotationMetadata) {
        List<SourceFetcherInfo> propertyValueMappers = this.getSourceFetcherInfos(propertyValue, StringUtils.EMPTY);
        return Optional.of(new TypeTransformationPropertyValueMapper(new MultiSourcesPropertyValueMapper(
                propertyValueMappers), propertyValue.getType()));
    }

    private List<SourceFetcherInfo> getSourceFetcherInfos(PropertyValue propertyValue, String destinationName) {
        List<SourceFetcherInfo> sourceFetcherInfos = new ArrayList<>();
        for (Field field : ReflectionUtils.getDeclaredFields(propertyValue.getType(), true)) {
            PropertyValue fieldPropertyValue = PropertyValue.createFieldValue(field);
            String fieldPath = destinationName + DESTINATION_NAME_SEPARATOR + fieldPropertyValue.getName();
            Optional<AnnotatedElement> element = fieldPropertyValue.getElement();
            if (!element.isPresent()) {
                continue;
            }
            AnnotationMetadata annotationMetadata = this.annotationResolver.resolve(element.get());
            if (SchemaTypeUtils.isObjectType(field.getType())) {
                sourceFetcherInfos.addAll(this.getSourceFetcherInfos(fieldPropertyValue, fieldPath));
                continue;
            }
            SourceFetcher sourceFetcher;
            boolean isArrayType;
            if (annotationMetadata.isAnnotationNotPresent(RequestParam.class)) {
                sourceFetcher = SOURCE_FETCHER_MAPPING.get(Source.QUERY)
                        .apply(ParamValue.custom().name(field.getName()).build());
                isArrayType = SchemaTypeUtils.isArrayType(field.getType());
            } else {
                RequestParam annotation = annotationMetadata.getAnnotation(RequestParam.class);
                sourceFetcher = this.getSourceFetcher(annotation);
                isArrayType = this.isArray(fieldPropertyValue, annotationMetadata);
            }
            sourceFetcherInfos.add(new SourceFetcherInfo(sourceFetcher,
                    fieldPath.substring(DESTINATION_NAME_SEPARATOR.length()),
                    isArrayType));
        }
        return sourceFetcherInfos;
    }

    private Boolean isArray(PropertyValue propertyValue, AnnotationMetadata annotationMetadata) {
        return IS_ARRAY_MAPPING.entrySet()
                .stream()
                .filter(entry -> annotationMetadata.isAnnotationPresent(entry.getKey()))
                .findFirst()
                .map(entry -> entry.getValue().apply(propertyValue))
                .orElseThrow(() -> new IllegalStateException("Failed to judge whether property value is array."));
    }

    private SourceFetcher getSourceFetcher(RequestParam requestParam) {
        Function<ParamValue, SourceFetcher> function = SOURCE_FETCHER_MAPPING.get(requestParam.in());
        return function.apply(ParamValue.custom()
                .name(requestParam.name())
                .in(requestParam.in())
                .defaultValue(requestParam.defaultValue())
                .required(requestParam.required())
                .build());
    }
}
