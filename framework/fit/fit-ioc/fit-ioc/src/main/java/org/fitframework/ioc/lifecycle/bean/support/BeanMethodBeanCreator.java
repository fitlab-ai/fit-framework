// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.lifecycle.bean.support;

import static org.fitframework.inspection.Validation.isTrue;
import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.ioc.BeanCreationException;
import org.fitframework.ioc.BeanMetadata;
import org.fitframework.ioc.BeanNotFoundException;
import org.fitframework.ioc.lifecycle.bean.BeanCreator;
import org.fitframework.util.ReflectionUtils;
import org.fitframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 为 {@link BeanCreator} 提供基于 Bean 方法的实现。
 *
 * @author 梁济时
 * @author 季聿阶
 * @since 2022-05-12
 */
public class BeanMethodBeanCreator extends AbstractBeanCreator {
    private final Method method;

    /**
     * 使用所在的Bean容器、所属Bean的元数据及创建Bean的方法初始化 {@link BeanMethodBeanCreator} 类的新实例。
     *
     * @param metadata 表示所属Bean的元数据的 {@link BeanMetadata}。
     * @param method 表示用以创建Bean的方法的 {@link Method}。
     * @throws IllegalArgumentException {@code container}、{@code metadata} 或 {@code method} 为 {@code null}。
     */
    public BeanMethodBeanCreator(BeanMetadata metadata, Method method) {
        super(metadata);
        this.method = notNull(method, "The method to create bean cannot be null.");
        this.method.setAccessible(true);
    }

    @Override
    public Object create(Object[] arguments) {
        Arguments resolvedArguments = this.arguments(this.method, arguments);
        Object[] actual = resolvedArguments.get();
        isTrue(resolvedArguments.isComplete(),
                () -> new BeanCreationException(StringUtils.format(
                        "Arguments mismatched: {0}. [method={1}, arguments={2}]",
                        resolvedArguments.getMessage(),
                        ReflectionUtils.toString(this.method),
                        actual)));
        Object owner = this.metadata()
                .container()
                .factory(this.metadata().name())
                .orElseThrow(() -> new BeanNotFoundException(StringUtils.format(
                        "Bean not found in container. [container={0}, bean={1}]",
                        this.metadata().container().name(),
                        this.metadata().name())))
                .get();
        ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(this.method.getDeclaringClass().getClassLoader());
            return this.method.invoke(owner, actual);
        } catch (IllegalAccessException e) {
            throw new BeanCreationException(StringUtils.format(
                    "Failed to instantiate bean with specific factory method. [method={0}]",
                    ReflectionUtils.toString(this.method)), e);
        } catch (InvocationTargetException e) {
            throw new BeanCreationException(StringUtils.format(
                    "Failed to instantiate bean with specific factory method. [method={0}]",
                    ReflectionUtils.toString(this.method)), e.getCause());
        } finally {
            Thread.currentThread().setContextClassLoader(currentClassLoader);
        }
    }
}
