// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.annotation.ResponseStatus;
import org.fitframework.http.protocol.HttpResponseStatus;
import org.fitframework.ioc.BeanContainer;
import org.fitframework.ioc.annotation.AnnotationMetadata;
import org.fitframework.ioc.annotation.AnnotationMetadataResolver;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * 表示从 {@link ResponseStatus} 注解中获取响应状态的解析器。
 *
 * @author 季聿阶
 * @since 2023-01-11
 */
public class FitHttpResponseStatusResolver implements HttpResponseStatusResolver {
    private final BeanContainer container;

    FitHttpResponseStatusResolver(BeanContainer container) {
        this.container = notNull(container, "The bean container cannot be null.");
    }

    @Override
    public Optional<HttpResponseStatus> resolve(Method method) {
        AnnotationMetadataResolver annotationResolver = this.container.runtime().resolverOfAnnotations();
        AnnotationMetadata annotations = annotationResolver.resolve(method);
        if (annotations.isAnnotationPresent(ResponseStatus.class)) {
            return Optional.of(annotations.getAnnotation(ResponseStatus.class).code());
        }
        annotations = annotationResolver.resolve(method.getDeclaringClass());
        if (annotations.isAnnotationPresent(ResponseStatus.class)) {
            return Optional.of(annotations.getAnnotation(ResponseStatus.class).code());
        }
        return Optional.empty();
    }
}
