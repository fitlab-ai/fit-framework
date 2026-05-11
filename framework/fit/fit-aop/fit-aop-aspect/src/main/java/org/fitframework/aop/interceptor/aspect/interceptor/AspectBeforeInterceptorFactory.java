// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.aspect.interceptor;

import org.fitframework.aop.annotation.Before;
import org.fitframework.aop.interceptor.AdviceMethodInterceptor;
import org.fitframework.inspection.Nonnull;
import org.fitframework.ioc.BeanFactory;

import java.lang.reflect.Method;

/**
 * 将带有 {@link Before} 注解的方法包装成 {@link AdviceMethodInterceptor}。
 *
 * @author 季聿阶
 * @author 郭龙飞
 * @since 2023-03-08
 */
public class AspectBeforeInterceptorFactory extends AbstractAspectInterceptorFactory {
    /**
     * 初始化 {@link AspectBeforeInterceptorFactory} 的新实例。
     */
    public AspectBeforeInterceptorFactory() {
        super(Before.class);
    }

    @Override
    protected AdviceMethodInterceptor createConcreteMethodInterceptor(BeanFactory aspectFactory, Method method) {
        return new AspectBeforeInterceptor(aspectFactory, method);
    }

    @Override
    protected String getExpression(@Nonnull Method method) {
        return this.getAnnotations(method).getAnnotation(Before.class).pointcut();
    }

    @Override
    protected String getArgNames(@Nonnull Method method) {
        return this.getAnnotations(method).getAnnotation(Before.class).argNames();
    }
}
