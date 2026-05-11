// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.proxy.support;

import static org.fitframework.annotation.Order.PRETTY_HIGH;

import org.fitframework.annotation.Order;
import org.fitframework.aop.proxy.AopProxyFactory;
import org.fitframework.aop.proxy.FitProxy;
import org.fitframework.aop.proxy.InterceptSupport;
import org.fitframework.aop.util.ClassLoaderUtils;
import org.fitframework.util.ClassUtils;

import java.lang.reflect.Proxy;

/**
 * 表示通过 JDK 的动态代理技术实现的 {@link AopProxyFactory}。
 *
 * @author 季聿阶
 * @since 2022-05-04
 */
@Order(PRETTY_HIGH)
public class JdkDynamicAopProxyFactory implements AopProxyFactory {
    @Override
    public boolean support(Class<?> targetClass) {
        return targetClass.isInterface() || Proxy.isProxyClass(targetClass) || ClassUtils.isLambda(targetClass);
    }

    @Override
    public Object createProxy(InterceptSupport support) {
        ClassLoader classLoader = ClassLoaderUtils.getCommonChildClassLoader(support.getTargetClass(), FitProxy.class)
                .orElseThrow(() -> new IllegalStateException(
                        "Failed to get common child class loader when create proxy by jdk dynamic."));
        return Proxy.newProxyInstance(classLoader, new Class[] {
                support.getTargetClass(), FitProxy.class
        }, new JdkDynamicProxy(support));
    }
}
