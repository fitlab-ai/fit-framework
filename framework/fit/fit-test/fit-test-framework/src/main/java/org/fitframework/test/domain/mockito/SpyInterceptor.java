// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.test.domain.mockito;

import org.fitframework.annotation.Order;
import org.fitframework.ioc.BeanMetadata;
import org.fitframework.ioc.lifecycle.bean.BeanLifecycle;
import org.fitframework.ioc.lifecycle.bean.BeanLifecycleInterceptor;
import org.fitframework.util.ObjectUtils;
import org.fitframework.util.TypeUtils;

import org.mockito.Mockito;

import java.util.Collections;
import java.util.Set;

/**
 * 表示 {@link BeanLifecycleInterceptor} 的 Mockito 中的 {@link org.mockito.Spy} 实现。
 *
 * @author 季聿阶
 * @since 2024-07-28
 */
@Order(Order.HIGHEST)
public class SpyInterceptor implements BeanLifecycleInterceptor {
    private final Set<Class<?>> toSpyClasses;

    /**
     * 使用指定的待监视类集合初始化 {@link SpyInterceptor} 的新实例。
     *
     * @param toSpyClasses 表示待监视类集合的 {@link Set}{@code <}{@link Class}{@code <?>>}。
     */
    public SpyInterceptor(Set<Class<?>> toSpyClasses) {
        this.toSpyClasses = ObjectUtils.nullIf(toSpyClasses, Collections.emptySet());
    }

    @Override
    public boolean isInterceptionRequired(BeanMetadata metadata) {
        return this.toSpyClasses.contains(TypeUtils.toClass(metadata.type()));
    }

    @Override
    public Object decorate(BeanLifecycle lifecycle, Object bean) {
        Object initializedBean = lifecycle.decorate(bean);
        return Mockito.spy(initializedBean);
    }
}
