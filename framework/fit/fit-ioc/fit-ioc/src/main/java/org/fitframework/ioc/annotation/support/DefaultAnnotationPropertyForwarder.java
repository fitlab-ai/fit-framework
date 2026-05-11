// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.annotation.support;

import org.fitframework.annotation.Forward;
import org.fitframework.ioc.annotation.AnnotationProperties;
import org.fitframework.ioc.annotation.AnnotationProperty;
import org.fitframework.ioc.annotation.AnnotationPropertyForward;
import org.fitframework.ioc.annotation.AnnotationPropertyForwarder;
import org.fitframework.util.ObjectUtils;
import org.fitframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

/**
 * 为 {@link AnnotationPropertyForwarder} 提供默认实现。
 *
 * @author 梁济时
 * @since 2022-06-15
 */
public class DefaultAnnotationPropertyForwarder implements AnnotationPropertyForwarder {
    @Override
    public Optional<AnnotationPropertyForward> forward(Method propertyMethod) {
        Forward forward = propertyMethod.getAnnotation(Forward.class);
        if (forward == null) {
            return Optional.empty();
        }
        Class<? extends Annotation> targetAnnotation = Optional.of(forward.annotation())
                .filter(annotation -> !Objects.equals(annotation, Annotation.class))
                .orElseGet(() -> ObjectUtils.cast(propertyMethod.getDeclaringClass()));
        String targetProperty = Optional.of(forward.property())
                .map(StringUtils::trim)
                .filter(StringUtils::isNotEmpty)
                .orElseGet(propertyMethod::getName);
        AnnotationProperty target = AnnotationProperties.create(targetAnnotation, targetProperty);
        return Optional.of(new DefaultAnnotationPropertyForward(target, forward.converter()));
    }
}
