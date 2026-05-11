// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.value.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.value.PropertyValue;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * 表示参数类型的属性值。
 *
 * @author 邬涨财
 * @since 2023-11-14
 */
public class ParameterValue implements PropertyValue {
    private final Parameter parameter;

    /**
     * 使用指定的参数初始化 {@link ParameterValue} 的新实例。
     *
     * @param parameter 表示参数的 {@link Parameter}。
     * @throws IllegalArgumentException 当 {@code parameter} 为 {@code null} 时。
     */
    public ParameterValue(Parameter parameter) {
        this.parameter = notNull(parameter, "The parameter cannot be null.");
    }

    @Override
    public Class<?> getType() {
        return this.parameter.getType();
    }

    @Override
    public Type getParameterizedType() {
        return this.parameter.getParameterizedType();
    }

    @Override
    public Optional<AnnotatedElement> getElement() {
        return Optional.of(this.parameter);
    }

    @Override
    public String getName() {
        return this.parameter.getName();
    }
}
