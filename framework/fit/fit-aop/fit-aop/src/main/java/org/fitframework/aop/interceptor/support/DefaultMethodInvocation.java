// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.support;

import org.fitframework.aop.interceptor.MethodInvocation;
import org.fitframework.inspection.Nonnull;
import org.fitframework.inspection.Nullable;
import org.fitframework.inspection.Validation;
import org.fitframework.util.ObjectUtils;

import java.lang.reflect.Method;

/**
 * {@link MethodInvocation} 的默认实现。
 *
 * @author 季聿阶
 * @since 2022-05-17
 */
public class DefaultMethodInvocation implements MethodInvocation {
    private final Object target;
    private final Method method;
    private Object[] arguments;

    /**
     * 使用调用对象、调用方法和调用参数实例化 {@link DefaultMethodInvocation}。
     *
     * @param target 表示调用对象的 {@link Object}。
     * @param method 表示调用方法的 {@link Method}。
     * @param arguments 表示调用参数的 {@link Object}{@code []}。
     * @throws IllegalArgumentException 当 {@code method} 或 {@code arguments} 为 {@code null} 时。
     */
    public DefaultMethodInvocation(Object target, Method method, Object[] arguments) {
        this.target = target;
        this.method = Validation.notNull(method, "The method cannot be null.");
        this.arguments = ObjectUtils.getIfNull(arguments, () -> new Object[0]);
    }

    @Nullable
    @Override
    public Object getTarget() {
        return this.target;
    }

    @Nonnull
    @Override
    public Method getMethod() {
        return this.method;
    }

    @Nonnull
    @Override
    public Object[] getArguments() {
        return this.arguments;
    }

    @Override
    public void setArguments(@Nonnull Object[] arguments) {
        this.arguments = arguments;
    }
}
