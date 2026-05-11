// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.support;

import static org.fitframework.inspection.Validation.notNull;
import static org.fitframework.util.ObjectUtils.cast;
import static org.fitframework.util.ObjectUtils.nullIf;

import org.fitframework.ioc.BeanCreationException;
import org.fitframework.ioc.BeanFactory;
import org.fitframework.ioc.BeanMetadata;
import org.fitframework.ioc.lifecycle.bean.BeanLifecycle;
import org.fitframework.util.StringUtils;
import org.fitframework.util.support.AbstractDisposable;

/**
 * 为 {@link BeanFactory} 提供基类。
 *
 * @author 梁济时
 * @since 2022-08-04
 */
abstract class AbstractBeanFactory extends AbstractDisposable implements BeanFactory {
    private static final Object[] EMPTY_ARGUMENTS = new Object[0];

    private final BeanLifecycle lifecycle;

    /**
     * 使用Bean的生命周期创建 {@link AbstractBeanFactory} 类的新实例。
     *
     * @param lifecycle 表示Bean的生命周期的 {@link BeanLifecycle}。
     * @throws IllegalArgumentException {@code lifecycle} 为 {@code null}。
     */
    public AbstractBeanFactory(BeanLifecycle lifecycle) {
        this.lifecycle = notNull(lifecycle, "The lifecycle of bean to create cannot be null.");
    }

    @Override
    public BeanMetadata metadata() {
        return this.lifecycle.metadata();
    }

    /**
     * 获取Bean的生命周期。
     *
     * @return 表示Bean的生命周期的 {@link BeanLifecycle}。
     */
    protected final BeanLifecycle lifecycle() {
        return this.lifecycle;
    }

    @Override
    public <T> T get(Object[] arguments) {
        if (this.disposed()) {
            throw new BeanCreationException(StringUtils.format(
                    "The factory to manage bean has been disposed. [name={0}, type={1}]",
                    this.metadata().name(),
                    this.metadata().type()));
        }
        Object bean;
        try {
            bean = this.get0(nullIf(arguments, EMPTY_ARGUMENTS));
        } catch (Exception ex) {
            throw new BeanCreationException(StringUtils.format("Failed to create bean. [name={0}, type={1}, error={2}]",
                    this.metadata().name(),
                    this.metadata().type(),
                    ex.getMessage()), ex);
        }
        return cast(bean);
    }

    /**
     * 获取Bean实例。
     *
     * @param arguments 表示Bean的初始化参数的 {@link Object}{@code []}。
     * @return 表示Bean实例的 {@link Object}。
     */
    protected abstract Object get0(Object[] arguments);
}
