// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.aspect.interceptor;

import org.fitframework.aop.ProceedingJoinPoint;
import org.fitframework.aop.annotation.After;
import org.fitframework.aop.interceptor.MethodJoinPoint;
import org.fitframework.aop.interceptor.aspect.interceptor.inject.AspectParameterInjectionHelper;
import org.fitframework.aop.interceptor.aspect.interceptor.inject.ParameterInjection;
import org.fitframework.aop.interceptor.support.AfterInterceptor;
import org.fitframework.inspection.Nullable;
import org.fitframework.inspection.Validation;
import org.fitframework.ioc.BeanFactory;
import org.fitframework.ioc.annotation.AnnotationMetadata;

import java.lang.reflect.Method;

/**
 * {@link AfterInterceptor} 的 Aspect 实现。
 *
 * @author 季聿阶
 * @author 郭龙飞
 * @since 2022-05-19
 */
public class AspectAfterInterceptor extends AfterInterceptor {
    /**
     * 使用真实拦截的对象和真实拦截的方法来实例化一个 {@link AspectAfterInterceptor}。
     *
     * @param aspectFactory 表示真实拦截的对象的工厂的 {@link BeanFactory}。
     * @param interceptMethod 表示真实拦截的方法的 {@link Method}。
     */
    public AspectAfterInterceptor(BeanFactory aspectFactory, Method interceptMethod) {
        super(aspectFactory, interceptMethod);
        this.validateParameter(interceptMethod);
    }

    private void validateParameter(Method interceptMethod) {
        if (interceptMethod.getParameterCount() > 0) {
            Class<?>[] parameterTypes = interceptMethod.getParameterTypes();
            Validation.isTrue(parameterTypes[0] != ProceedingJoinPoint.class,
                    "The 1st parameter of @After interceptor in Aspect cannot be ProceedingJoinPoint.");
        }
    }

    @Override
    protected Object[] getAdvisorArgs(MethodJoinPoint joinPoint, @Nullable Object returnValue,
            @Nullable Throwable throwable) {
        Method method = this.getAdvisorMethod();
        AnnotationMetadata annotationMetadata = AspectParameterInjectionHelper.getAnnotationMetadata(method);
        After after = annotationMetadata.getAnnotation(After.class);
        String[] argNames = AspectParameterInjectionHelper.toArgNames(after.argNames());
        return AspectParameterInjectionHelper.getInjectionArgs(method,
                argNames,
                new ParameterInjection(this.getPointCut(), joinPoint),
                null,
                null);
    }
}
