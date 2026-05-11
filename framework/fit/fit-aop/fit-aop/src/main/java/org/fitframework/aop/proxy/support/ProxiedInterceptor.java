// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.proxy.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.aop.interceptor.MethodJoinPoint;
import org.fitframework.aop.interceptor.support.AbstractMethodInterceptor;
import org.fitframework.inspection.Nonnull;
import org.fitframework.inspection.Nullable;

/**
 * 调用被代理对象的方法拦截器。
 *
 * @author 季聿阶
 * @since 2022-05-25
 */
public class ProxiedInterceptor extends AbstractMethodInterceptor {
    private final ProxiedInvoker proxiedInvoker;

    /**
     * 使用被代理对象、被代理对象的方法和调用被代理对象的方法来实例化一个 {@link ProxiedInterceptor}。
     *
     * @param proxiedInvoker 表示调用被代理对象的方法的 {@link ProxiedInvoker}。
     */
    public ProxiedInterceptor(ProxiedInvoker proxiedInvoker) {
        this.proxiedInvoker = notNull(proxiedInvoker, "The proxied invoker cannot be null.");
    }

    @Nullable
    @Override
    public Object intercept(@Nonnull MethodJoinPoint methodJoinPoint) throws Throwable {
        return this.proxiedInvoker.invoke(methodJoinPoint.getProxiedInvocation());
    }
}
