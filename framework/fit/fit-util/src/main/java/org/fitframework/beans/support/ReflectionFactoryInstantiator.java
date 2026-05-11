// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.beans.support;

import static org.fitframework.inspection.Validation.notNull;
import static org.fitframework.util.ObjectUtils.cast;

import org.fitframework.beans.ObjectInstantiator;
import org.fitframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 通过 {@link sun.reflect.ReflectionFactory} 实现的任意对象生成器。
 * <p>该对象生成器会创建一个指定类型的无参构造方法，然后调用该构造方法生成实例。因为构造方法是动态生成的，
 * 因此不会调用任何当前已存在的构造方法，就算指定类型原本就有无参构造方法，也不会调用。</p>
 *
 * @author 季聿阶
 * @since 2022-05-10
 */
public class ReflectionFactoryInstantiator<T> implements ObjectInstantiator<T> {
    private final Constructor<T> constructor;

    /**
     * 使用指定类型实例化一个 {@link ReflectionFactoryInstantiator}。
     *
     * @param type 表示指定类型的 {@link Class}{@code <}{@link T}{@code >}。
     * @throws IllegalArgumentException 当 {@code type} 为 {@code null} 时。
     */
    public ReflectionFactoryInstantiator(Class<T> type) {
        notNull(type, "The class type cannot be null.");
        Constructor<Object> objectConstructor = getObjectConstructor();
        Class<?> reflectionFactoryClass = getReflectionFactoryClass();
        Object reflectionFactory = createReflectionFactory(reflectionFactoryClass);
        this.constructor = cast(ReflectionUtils.invoke(reflectionFactory,
                getNewConstructorForSerializationMethod(reflectionFactoryClass),
                type,
                objectConstructor));
    }

    @Override
    public T newInstance() {
        try {
            return this.constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }

    private static Constructor<Object> getObjectConstructor() {
        try {
            return Object.class.getConstructor((Class<?>[]) null);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }

    private static Class<?> getReflectionFactoryClass() {
        try {
            return Class.forName("sun.reflect.ReflectionFactory");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    private static Object createReflectionFactory(Class<?> reflectionFactoryClass) {
        Method method = ReflectionUtils.getDeclaredMethod(reflectionFactoryClass, "getReflectionFactory");
        return ReflectionUtils.invoke(null, method);
    }

    private static Method getNewConstructorForSerializationMethod(Class<?> reflectionFactoryClass) {
        return ReflectionUtils.getDeclaredMethod(reflectionFactoryClass,
                "newConstructorForSerialization",
                Class.class,
                Constructor.class);
    }
}
