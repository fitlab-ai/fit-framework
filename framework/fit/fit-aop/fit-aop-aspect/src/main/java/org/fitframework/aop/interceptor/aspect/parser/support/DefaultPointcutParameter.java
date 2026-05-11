// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.aspect.parser.support;

import org.fitframework.aop.interceptor.aspect.parser.PointcutParameter;

/**
 * {@link PointcutParameter} 默认实现。
 *
 * @author 郭龙飞
 * @since 2023-03-08
 */
public class DefaultPointcutParameter implements PointcutParameter {
    private final String name;
    private final Class<?> type;
    private Object binding;

    /**
     * 使用指定的名称和类型初始化 {@link DefaultPointcutParameter} 的新实例。
     *
     * @param name 表示名称的 {@link String}。
     * @param type 表示类型的 {@link Class}{@code <?>}。
     */
    public DefaultPointcutParameter(String name, Class<?> type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Class<?> getType() {
        return this.type;
    }

    @Override
    public Object getBinding() {
        return this.binding;
    }

    @Override
    public void setBinding(Object boundValue) {
        this.binding = boundValue;
    }
}
