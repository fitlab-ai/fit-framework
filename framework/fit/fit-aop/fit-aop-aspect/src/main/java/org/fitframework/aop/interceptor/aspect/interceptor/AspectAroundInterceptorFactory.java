// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.aspect.interceptor;

import org.fitframework.aop.annotation.Around;
import org.fitframework.aop.interceptor.AdviceMethodInterceptor;
import org.fitframework.inspection.Nonnull;
import org.fitframework.ioc.BeanFactory;

import java.lang.reflect.Method;

/**
 * 将带有 {@link Around} 注解的方法包装成 {@link AdviceMethodInterceptor}。
 *
 * @author 季聿阶
 * @author 郭龙飞
 * @since 2022-05-20
 */
public class AspectAroundInterceptorFactory extends AbstractAspectInterceptorFactory {
    /**
     * 初始化 {@link AspectAroundInterceptorFactory} 的新实例。
     */
    public AspectAroundInterceptorFactory() {
        super(Around.class);
    }

    @Override
    protected AdviceMethodInterceptor createConcreteMethodInterceptor(BeanFactory aspectFactory, Method method) {
        return new AspectAroundInterceptor(aspectFactory, method);
    }

    @Override
    protected String getExpression(@Nonnull Method method) {
        return this.getAnnotations(method).getAnnotation(Around.class).pointcut();
    }

    @Override
    protected String getArgNames(@Nonnull Method method) {
        return this.getAnnotations(method).getAnnotation(Around.class).argNames();
    }
}
