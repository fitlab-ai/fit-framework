// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.aspect.interceptor;

import org.fitframework.aop.annotation.After;
import org.fitframework.aop.interceptor.AdviceMethodInterceptor;
import org.fitframework.inspection.Nonnull;
import org.fitframework.ioc.BeanFactory;

import java.lang.reflect.Method;

/**
 * 将带有 {@link After} 注解的方法包装成 {@link AdviceMethodInterceptor}。
 *
 * @author 季聿阶
 * @author 郭龙飞
 * @since 2022-05-14
 */
public class AspectAfterInterceptorFactory extends AbstractAspectInterceptorFactory {
    /**
     * 初始化 {@link AspectAfterInterceptorFactory} 的新实例。
     */
    public AspectAfterInterceptorFactory() {
        super(After.class);
    }

    @Override
    protected AdviceMethodInterceptor createConcreteMethodInterceptor(BeanFactory aspectFactory, Method method) {
        return new AspectAfterInterceptor(aspectFactory, method);
    }

    @Override
    protected String getExpression(@Nonnull Method method) {
        return this.getAnnotations(method).getAnnotation(After.class).pointcut();
    }

    @Override
    protected String getArgNames(@Nonnull Method method) {
        return this.getAnnotations(method).getAnnotation(After.class).argNames();
    }
}
