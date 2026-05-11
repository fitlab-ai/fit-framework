// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.support;

import org.fitframework.aop.interceptor.MethodJoinPoint;
import org.fitframework.inspection.Nonnull;
import org.fitframework.inspection.Nullable;
import org.fitframework.ioc.BeanFactory;

import java.lang.reflect.Method;

/**
 * 用于在方法调用之后且成功获取返回值后生效的方法拦截器。
 *
 * @author 季聿阶
 * @since 2022-05-11
 */
public class AfterReturningInterceptor extends AbstractAdviceMethodInterceptor {
    /**
     * 使用拦截建议的对象和拦截建议的方法来实例化一个 {@link AfterReturningInterceptor}。
     *
     * @param aspectFactory 表示真实拦截的对象的 {@link BeanFactory}。
     * @param advisorMethod 表示真实拦截的方法的 {@link Method}。
     */
    public AfterReturningInterceptor(BeanFactory aspectFactory, Method advisorMethod) {
        super(aspectFactory, advisorMethod);
    }

    @Nullable
    @Override
    public Object intercept(@Nonnull MethodJoinPoint methodJoinPoint) throws Throwable {
        Object result = methodJoinPoint.proceed();
        this.invokeAdvisorPoint(methodJoinPoint, result, null);
        return result;
    }
}
