// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.proxy;

import org.fitframework.aop.interceptor.MethodInterceptor;

import java.util.List;

/**
 * 表示拦截事件支持信息。
 *
 * @author 季聿阶
 * @since 2022-05-04
 */
public interface InterceptSupport {
    /**
     * 获取被代理对象的类型。
     *
     * @return 表示被代理对象的类型的 {@link Class}{@code <}{@link Object}{@code >}。
     */
    Class<?> getTargetClass();

    /**
     * 获取被代理对象。
     *
     * @return 表示被代理对象的 {@link Object}。
     */
    Object getTarget();

    /**
     * 获取调用的方法拦截器列表。
     *
     * @return 表示调用的方法拦截器列表的 {@link List}{@code <}{@link MethodInterceptor}{@code >}。
     */
    List<MethodInterceptor> getMethodInterceptors();
}
