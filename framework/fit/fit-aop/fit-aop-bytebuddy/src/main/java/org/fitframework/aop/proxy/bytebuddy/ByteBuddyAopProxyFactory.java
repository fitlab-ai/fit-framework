// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.proxy.bytebuddy;

import static org.fitframework.annotation.Order.HIGH;

import org.fitframework.annotation.Order;
import org.fitframework.aop.proxy.AopProxyFactory;
import org.fitframework.aop.proxy.FitProxy;
import org.fitframework.aop.proxy.InterceptSupport;
import org.fitframework.aop.proxy.support.JdkDynamicProxy;
import org.fitframework.aop.util.ClassLoaderUtils;
import org.fitframework.beans.support.ReflectionFactoryInstantiator;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Default;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.RandomString;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 表示使用 ByteBuddy 技术实现的 {@link AopProxyFactory}。
 *
 * @author 詹高扬
 * @author 季聿阶
 * @since 2022-08-02
 */
@Order(HIGH)
public class ByteBuddyAopProxyFactory implements AopProxyFactory {
    /**
     * 缓存已经代理过的类对象，键表示被代理的类对象，值表示对应代理类对象
     */
    private final Map<Class<?>, Class<?>> cache = new ConcurrentHashMap<>();

    @Override
    public boolean support(Class<?> targetClass) {
        return true;
    }

    @Override
    public Object createProxy(InterceptSupport support) {
        Class<?> proxiedClass =
                this.cache.computeIfAbsent(support.getTargetClass(), key -> this.generateProxiedClass(support));
        ReflectionFactoryInstantiator<?> instantiator = new ReflectionFactoryInstantiator<>(proxiedClass);
        return instantiator.newInstance();
    }

    private Class<?> generateProxiedClass(InterceptSupport support) {
        ClassLoader classLoader = ClassLoaderUtils.getCommonChildClassLoader(support.getTargetClass(), FitProxy.class)
                .orElseThrow(() -> new IllegalStateException(
                        "Failed to get common child class loader when generate proxied class by bytebuddy."));
        return new ByteBuddy().subclass(support.getTargetClass())
                .implement(FitProxy.class)
                .name(support.getTargetClass().getName() + "$$Fit$ByteBuddy$$" + RandomString.make(8))
                .method(ElementMatchers.isMethod())
                .intercept(InvocationHandlerAdapter.of(new JdkDynamicProxy(support)))
                .make()
                .load(classLoader, Default.INJECTION)
                .getLoaded();
    }
}
