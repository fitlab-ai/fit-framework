// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.beans.convert;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.inspection.Nonnull;
import org.fitframework.util.ObjectUtils;
import org.fitframework.util.ReflectionUtils;
import org.fitframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 为 {@link ValueConverter} 提供基于 {@link Method} 类的实现。
 *
 * @author 梁济时
 * @since 2022-12-27
 */
public class MethodValueConverter implements ValueConverter {
    private final Class<?> source;
    private final Class<?> target;
    private final Method method;

    /**
     * 使用用以转换对象的方法初始化 {@link MethodValueConverter} 类的新实例。
     *
     * @param method 表示用以转换的方法的 {@link Method}。
     * @throws IllegalArgumentException {@code method} 为 {@code null} 或不是静态方法，或所包含的参数数量不是 {@code
     * 1}，或将抛出首检异常。
     */
    public MethodValueConverter(Method method) {
        this.method = notNull(method, "The method to convert object cannot be null.");
        this.method.setAccessible(true);
        if (!Modifier.isStatic(method.getModifiers())) {
            throw new IllegalArgumentException(StringUtils.format(
                    "The method to convert object must be static. [method={0}]",
                    ReflectionUtils.signatureOf(method)));
        }
        if (method.getParameterCount() != 1) {
            throw new IllegalArgumentException(StringUtils.format(
                    "The method to convert object must contain one and only one parameter. [method={0}]",
                    ReflectionUtils.signatureOf(method)));
        }
        Optional<Class<?>> checkedException =
                Stream.of(method.getExceptionTypes()).filter(ReflectionUtils::isCheckedException).findAny();
        if (checkedException.isPresent()) {
            throw new IllegalArgumentException(StringUtils.format(
                    "The method to convert object cannot throw any checked exception. [method={0}, exception={1}]",
                    ReflectionUtils.signatureOf(method),
                    checkedException.get().getName()));
        }
        this.source = this.method.getParameters()[0].getType();
        this.target = this.method.getReturnType();
    }

    @Override
    public Class<?> source() {
        return this.source;
    }

    @Override
    public Class<?> target() {
        return this.target;
    }

    @Override
    public Object convert(@Nonnull Object value) {
        try {
            return this.method.invoke(null, value);
        } catch (IllegalAccessException ex) {
            throw new IllegalStateException(StringUtils.format(
                    "Failed to access method to convert object. " + "[method={0}]",
                    ReflectionUtils.signatureOf(this.method)), ex);
        } catch (InvocationTargetException ex) {
            Throwable cause = ex.getCause();
            if (cause instanceof Error) {
                throw ObjectUtils.<Error>cast(cause);
            } else {
                throw ObjectUtils.<RuntimeException>cast(cause);
            }
        }
    }

    @Override
    public String toString() {
        return ReflectionUtils.signatureOf(this.method);
    }
}
