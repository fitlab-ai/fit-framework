// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.lifecycle.bean.support;

import org.fitframework.inspection.Validation;
import org.fitframework.ioc.BeanCreationException;
import org.fitframework.ioc.BeanDefinitionException;
import org.fitframework.ioc.lifecycle.bean.BeanInjector;
import org.fitframework.ioc.lifecycle.bean.ValueSupplier;
import org.fitframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 为 {@link BeanInjector} 提供基于字段注入的实现。
 *
 * @author 梁济时
 * @since 2022-04-28
 */
public class FieldBeanInjector implements BeanInjector {
    private final Field field;
    private final ValueSupplier supplier;

    /**
     * 使用待注入的字段和值初始化 {@link FieldBeanInjector} 类的新实例。
     *
     * @param field 表示待注入的字段的 {@link Field}。
     * @param supplier 表示待注入的值的提供程序的 {@link ValueSupplier}。
     * @throws IllegalArgumentException {@code field} 为 {@code null}。
     * @throws BeanDefinitionException {@code field} 被 {@code final} 或 {@code static} 修饰。
     */
    public FieldBeanInjector(Field field, ValueSupplier supplier) {
        this.field = Validation.notNull(field, "The field to inject cannot be null.");
        if (Modifier.isFinal(field.getModifiers())) {
            throw new BeanDefinitionException(StringUtils.format("Cannot inject a final field. [field={0}.{1}]",
                    field.getDeclaringClass().getName(),
                    field.getName()));
        } else if (Modifier.isStatic(field.getModifiers())) {
            throw new BeanDefinitionException(StringUtils.format("Cannot inject a static field. [field={0}.{1}]",
                    field.getDeclaringClass().getName(),
                    field.getName()));
        } else {
            this.supplier = supplier;
            this.field.setAccessible(true);
        }
    }

    @Override
    public void inject(Object bean) {
        Object actualValue = ValueSupplier.real(this.supplier);
        try {
            this.field.set(bean, actualValue);
        } catch (IllegalAccessException e) {
            throw new BeanCreationException(StringUtils.format("Failed to inject value to field. [field={0}, "
                            + "value={1}]",
                    this.field.getName(),
                    actualValue), e);
        }
    }
}
