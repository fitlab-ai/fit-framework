// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.support;

import org.fitframework.broker.GenericableMethod;
import org.fitframework.inspection.Nullable;

import java.lang.reflect.Method;

/**
 * 表示可修改的 {@link GenericableMethod}。
 *
 * @author 季聿阶
 * @since 2023-03-29
 */
public class ConfigurableGenericableMethod implements GenericableMethod {
    private Method method;

    @Nullable
    @Override
    public Method method() {
        return this.method;
    }

    /**
     * 设置泛服务的方法。
     *
     * @param method 表示泛服务的方法的 {@link Method}。
     */
    public void method(Method method) {
        this.method = method;
    }
}
