// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.aspect.interceptor;

import org.fitframework.aop.annotation.AfterReturning;
import org.fitframework.aop.interceptor.AdviceMethodInterceptor;
import org.fitframework.inspection.Nonnull;
import org.fitframework.ioc.BeanFactory;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 将带有 {@link AfterReturning} 注解的方法包装成 {@link AdviceMethodInterceptor}。
 *
 * @author 季聿阶
 * @author 郭龙飞
 * @since 2022-05-14
 */
public class AspectAfterReturningInterceptorFactory extends AbstractAspectInterceptorFactory {
    /**
     * 初始化 {@link AspectAfterReturningInterceptorFactory} 的新实例。
     */
    public AspectAfterReturningInterceptorFactory() {
        super(AfterReturning.class);
    }

    @Override
    protected AdviceMethodInterceptor createConcreteMethodInterceptor(BeanFactory aspectFactory, Method method) {
        return new AspectAfterReturningInterceptor(aspectFactory, method);
    }

    @Override
    protected String getExpression(@Nonnull Method method) {
        return this.getAnnotations(method).getAnnotation(AfterReturning.class).pointcut();
    }

    @Override
    protected String getArgNames(@Nonnull Method method) {
        return this.getAnnotations(method).getAnnotation(AfterReturning.class).argNames();
    }

    @Override
    protected boolean shouldIgnore(@Nonnull Method method, String argName) {
        return Objects.equals(argName, this.getAnnotations(method).getAnnotation(AfterReturning.class).returning());
    }
}
