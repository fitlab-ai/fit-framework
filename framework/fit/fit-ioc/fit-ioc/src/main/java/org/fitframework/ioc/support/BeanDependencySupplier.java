// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.support;

import org.fitframework.ioc.BeanDependency;
import org.fitframework.ioc.DependencyNotFoundException;
import org.fitframework.ioc.DependencyResolvingResult;
import org.fitframework.ioc.lifecycle.bean.ValueSupplier;
import org.fitframework.util.StringUtils;

/**
 * 为 {@link ValueSupplier} 提供基于 {@link BeanDependency} 的实现。
 *
 * @author 梁济时
 * @since 2022-06-27
 */
class BeanDependencySupplier implements ValueSupplier {
    private final BeanDependency dependency;

    /**
     * 使用依赖初始化 {@link BeanDependencySupplier} 类的新实例。
     *
     * @param dependency 表示依赖的 {@link BeanDependency}。
     */
    public BeanDependencySupplier(BeanDependency dependency) {
        this.dependency = dependency;
    }

    @Override
    public Object get() {
        DependencyResolvingResult result = this.dependency.resolve();
        Object value = result.resolved() ? result.get() : null;
        if (this.dependency.required() && value == null) {
            throw new DependencyNotFoundException(StringUtils.format(
                    "Dependency required but not found. [type={0}, name={1}]",
                    this.dependency.type().getTypeName(), this.dependency.name()));
        } else {
            return value;
        }
    }
}
