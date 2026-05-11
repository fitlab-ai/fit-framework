// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.aspect.interceptor;

import org.fitframework.aop.ProceedingJoinPoint;
import org.fitframework.aop.annotation.AfterReturning;
import org.fitframework.aop.interceptor.MethodJoinPoint;
import org.fitframework.aop.interceptor.aspect.interceptor.inject.AspectParameterInjectionHelper;
import org.fitframework.aop.interceptor.aspect.interceptor.inject.ParameterInjection;
import org.fitframework.aop.interceptor.aspect.interceptor.inject.ValueInjection;
import org.fitframework.aop.interceptor.support.AfterReturningInterceptor;
import org.fitframework.inspection.Nullable;
import org.fitframework.inspection.Validation;
import org.fitframework.ioc.BeanFactory;
import org.fitframework.ioc.annotation.AnnotationMetadata;

import java.lang.reflect.Method;

/**
 * {@link AfterReturningInterceptor} 的 Aspect 实现。
 *
 * @author 季聿阶
 * @author 郭龙飞
 * @since 2022-05-19
 */
public class AspectAfterReturningInterceptor extends AfterReturningInterceptor {
    /**
     * 使用真实拦截的对象和真实拦截的方法来实例化一个 {@link AspectAfterReturningInterceptor}。
     *
     * @param aspectFactory 表示真实拦截的对象的工厂的 {@link BeanFactory}。
     * @param interceptMethod 表示真实拦截的方法的 {@link Method}。
     */
    public AspectAfterReturningInterceptor(BeanFactory aspectFactory, Method interceptMethod) {
        super(aspectFactory, interceptMethod);
        this.validateParameter(interceptMethod);
    }

    private void validateParameter(Method interceptMethod) {
        if (interceptMethod.getParameterCount() > 0) {
            Class<?>[] parameterTypes = interceptMethod.getParameterTypes();
            Validation.isTrue(parameterTypes[0] != ProceedingJoinPoint.class,
                    "The 1st parameter of @AfterReturning interceptor in Aspect cannot be ProceedingJoinPoint.");
        }
    }

    @Override
    protected Object[] getAdvisorArgs(MethodJoinPoint joinPoint, @Nullable Object returnValue,
            @Nullable Throwable throwable) {
        Method method = this.getAdvisorMethod();
        AnnotationMetadata annotationMetadata = AspectParameterInjectionHelper.getAnnotationMetadata(method);
        AfterReturning afterReturning = annotationMetadata.getAnnotation(AfterReturning.class);
        String[] argNames = AspectParameterInjectionHelper.toArgNames(afterReturning.argNames());
        return AspectParameterInjectionHelper.getInjectionArgs(method,
                argNames,
                new ParameterInjection(this.getPointCut(), joinPoint),
                new ValueInjection(afterReturning.returning(), returnValue),
                null);
    }
}
