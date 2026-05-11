// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.websocket.server;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.server.handler.PropertyValueMapper;
import org.fitframework.http.server.handler.PropertyValueMapperResolver;
import org.fitframework.http.websocket.annotation.BinaryMessage;
import org.fitframework.ioc.annotation.AnnotationMetadataResolver;
import org.fitframework.value.PropertyValue;

import java.util.Optional;

/**
 * 表示 {@link WebSocketBinaryMessageMapper} 的映射器。
 *
 * @author 季聿阶
 * @since 2023-12-11
 */
public class WebSocketBinaryMessageMapperResolver implements PropertyValueMapperResolver {
    private final AnnotationMetadataResolver annotationResolver;

    /**
     * 使用指定的注解解析器初始化 {@link WebSocketBinaryMessageMapperResolver} 的新实例。
     *
     * @param annotationResolver 表示注解解析器的 {@link AnnotationMetadataResolver}。
     */
    public WebSocketBinaryMessageMapperResolver(AnnotationMetadataResolver annotationResolver) {
        this.annotationResolver = annotationResolver;
    }

    @Override
    public Optional<PropertyValueMapper> resolve(PropertyValue propertyValue) {
        return notNull(propertyValue, "The property value cannot be null.").getElement()
                .map(this.annotationResolver::resolve)
                .filter(annotations -> annotations.isAnnotationPresent(BinaryMessage.class))
                .map(annotations -> new WebSocketBinaryMessageMapper());
    }
}
