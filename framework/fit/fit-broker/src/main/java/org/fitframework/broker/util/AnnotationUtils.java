// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.util;

import static org.fitframework.inspection.Validation.isTrue;
import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.annotation.Fit;
import org.fitframework.annotation.Genericable;
import org.fitframework.ioc.annotation.AnnotationMetadata;
import org.fitframework.ioc.annotation.AnnotationMetadataResolvers;
import org.fitframework.util.ObjectUtils;
import org.fitframework.util.StringUtils;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * 为 {@link Genericable} 和 {@link Fit} 注解提供工具方法。
 *
 * @author 梁济时
 * @since 2020-08-21
 */
public class AnnotationUtils {
    /**
     * 隐藏默认构造方法，避免工具类被实例化。
     */
    private AnnotationUtils() {}

    /**
     * 获取指定服务定义的唯一标识。
     *
     * @param genericableClass 表示服务类型的 {@link Class}{@code <?>}。
     * @return 表示服务唯一标识的 {@link String}。
     * @throws IllegalArgumentException 当 {@code genericableClass} 为 {@code null} 或不是一个接口时。
     */
    public static Optional<String> getGenericableId(Class<?> genericableClass) {
        notNull(genericableClass, "The genericable class cannot be null.");
        isTrue(genericableClass.isInterface(),
                "The genericable class is not a interface. [class={0}]",
                genericableClass.getName());
        return getGenericableId(ObjectUtils.<AnnotatedElement>cast(genericableClass));
    }

    /**
     * 获取指定服务定义的唯一标识。
     *
     * @param genericableMethod 表示服务方法的 {@link Method}。
     * @return 表示服务唯一标识的 {@link String}。
     * @throws IllegalArgumentException 当 {@code genericableMethod} 为 {@code null} 时。
     */
    public static Optional<String> getGenericableId(Method genericableMethod) {
        notNull(genericableMethod, "The genericable method cannot be null.");
        return getGenericableId(ObjectUtils.<AnnotatedElement>cast(genericableMethod));
    }

    private static Optional<String> getGenericableId(AnnotatedElement element) {
        AnnotationMetadata annotations = AnnotationMetadataResolvers.create().resolve(element);
        return Optional.ofNullable(annotations.getAnnotation(Genericable.class))
                .map(Genericable::id)
                .filter(StringUtils::isNotBlank);
    }
}
