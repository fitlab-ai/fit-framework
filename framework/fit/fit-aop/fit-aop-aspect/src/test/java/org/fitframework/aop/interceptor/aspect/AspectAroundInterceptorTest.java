// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.aspect;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.fitframework.aop.interceptor.aspect.interceptor.AspectAroundInterceptor;
import org.fitframework.ioc.BeanFactory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * {@link AspectAroundInterceptor} 单元测试。
 *
 * @author 季聿阶
 * @since 2022-05-23
 */
@DisplayName("测试 AspectAroundInterceptor")
public class AspectAroundInterceptorTest {
    @SuppressWarnings("resource")
    @Test
    @DisplayName("当 @Around 定义的切面没有参数时，生成方法拦截器失败")
    void givenAroundMethodNoParametersThenFailToGenerateMethodInterceptor() throws NoSuchMethodException {
        Method method = AspectAroundInterceptorTest.class.getDeclaredMethod("aroundWithoutParameters");
        BeanFactory aspectFactory = mock(BeanFactory.class);
        Object aspect = new AspectAroundInterceptorTest();
        when(aspectFactory.get()).thenReturn(aspect);
        IllegalArgumentException exception = catchThrowableOfType(IllegalArgumentException.class,
                () -> new AspectAroundInterceptor(aspectFactory, method));
        assertThat(exception).hasMessage(
                "@Around interceptor in Aspect must have at least 1 parameter: ProceedingJoinPoint.");
    }

    @SuppressWarnings("resource")
    @Test
    @DisplayName("当 @Around 定义的切面缺少 ProceedingJoinPoint 参数时，生成方法拦截器失败")
    void givenAroundMethodNoProceedingJoinPointThenFailToGenerateMethodInterceptor() throws NoSuchMethodException {
        Method method =
                AspectAroundInterceptorTest.class.getDeclaredMethod("aroundWithoutProceedingJoinPoint", String.class);
        BeanFactory aspectFactory = mock(BeanFactory.class);
        Object aspect = new AspectAroundInterceptorTest();
        when(aspectFactory.get()).thenReturn(aspect);
        IllegalArgumentException exception = catchThrowableOfType(IllegalArgumentException.class,
                () -> new AspectAroundInterceptor(aspectFactory, method));
        assertThat(exception).hasMessage(
                "The 1st parameter of @Around interceptor in Aspect must be ProceedingJoinPoint.");
    }

    Optional<Object> aroundWithoutParameters() {
        return Optional.empty();
    }

    Object aroundWithoutProceedingJoinPoint(String name) {
        return name;
    }
}
