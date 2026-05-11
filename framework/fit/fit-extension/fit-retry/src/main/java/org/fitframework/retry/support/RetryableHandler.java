// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.retry.support;

import org.fitframework.annotation.Component;
import org.fitframework.aop.MethodSignature;
import org.fitframework.aop.ProceedingJoinPoint;
import org.fitframework.aop.Signature;
import org.fitframework.aop.annotation.Around;
import org.fitframework.aop.annotation.Aspect;
import org.fitframework.ioc.BeanContainer;
import org.fitframework.retry.Condition;
import org.fitframework.retry.RecoverCallable;
import org.fitframework.retry.RetryExecutor;
import org.fitframework.retry.annotation.Backoff;
import org.fitframework.retry.annotation.Recover;
import org.fitframework.retry.annotation.Retryable;
import org.fitframework.retry.backoff.ExponentialRetryBackOff;
import org.fitframework.retry.condition.ConditionComposite;
import org.fitframework.retry.condition.ExceptionCondition;
import org.fitframework.retry.condition.TimesLimitedRetryCondition;
import org.fitframework.util.AnnotationUtils;
import org.fitframework.util.ObjectUtils;
import org.fitframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * 重试机制的处理器。
 *
 * @author 邬涨财
 * @since 2023-02-21
 */
@Aspect
@Component
public class RetryableHandler {
    private final BeanContainer beanContainer;

    public RetryableHandler(BeanContainer beanContainer) {
        this.beanContainer = beanContainer;
    }

    @Around("@annotation(org.fitframework.retry.annotation.Retryable)")
    private Object handle(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        if (!(signature instanceof MethodSignature)) {
            String msg = "Failed to parse retryable annotation: the annotation only used in method.";
            throw new IllegalStateException(msg);
        }
        MethodSignature methodSignature = ObjectUtils.cast(signature);
        Object target = joinPoint.getTarget();
        Method method = ReflectionUtils.getDeclaredMethod(target.getClass(),
                methodSignature.getName(),
                methodSignature.getParameterTypes());
        Object[] args = joinPoint.getArgs();
        Retryable retryable = AnnotationUtils.getAnnotation(this.beanContainer, method, Retryable.class)
                .orElseThrow(() -> new IllegalStateException(
                        "Failed to parse retryable annotation: the annotation is not exist."));
        Backoff backoffAnnotation = retryable.backoff();
        RetryExecutor<Object> retryExecutor = RetryExecutor.builder()
                .recoverCondition(new ExceptionCondition(Arrays.asList(retryable.value())))
                .retryCondition(this.buildRetryCondition(retryable))
                .backOff(this.buildBackOff(backoffAnnotation))
                .recover(this.buildRecover(retryable.recover(), method, target, args))
                .callable(this.buildCallable(joinPoint, args))
                .build();
        return retryExecutor.execute();
    }

    private Condition buildRetryCondition(Retryable retryable) {
        Condition timesLimitedRetryCondition = new TimesLimitedRetryCondition(retryable.maxAttempts());
        List<Class<? extends Throwable>> capturedExceptions = Arrays.asList(retryable.value());
        Condition exceptionRetryCondition = new ExceptionCondition(capturedExceptions);
        return ConditionComposite.combine(timesLimitedRetryCondition, exceptionRetryCondition);
    }

    private ExponentialRetryBackOff<Object> buildBackOff(Backoff backoffAnnotation) {
        return new ExponentialRetryBackOff<>(backoffAnnotation.minDelay(),
                backoffAnnotation.maxDelay(),
                backoffAnnotation.multiplier());
    }

    private RecoverCallable<Object> buildRecover(String recover, Method retryableMethod, Object target, Object[] args) {
        Method[] methods = retryableMethod.getDeclaringClass().getDeclaredMethods();
        Method recoverMethod = Arrays.stream(methods)
                .filter(searchedMethod -> this.isMethodSearched(recover, retryableMethod, searchedMethod))
                .findAny()
                .orElseThrow(() -> new IllegalStateException(
                        "Failed to parse recover method: recover method is not found."));
        return (exception) -> {
            LinkedList<Object> recoverArgs = new LinkedList<>(Arrays.asList(args));
            recoverArgs.addFirst(exception);
            return ReflectionUtils.invoke(target, recoverMethod, recoverArgs.toArray(new Object[0]));
        };
    }

    private boolean isMethodSearched(String recover, Method retryableMethod, Method searchedMethod) {
        if (!Objects.equals(searchedMethod.getName(), recover)) {
            return false;
        }
        if (!AnnotationUtils.getAnnotation(this.beanContainer, searchedMethod, Recover.class).isPresent()) {
            return false;
        }
        return this.compareParameters(searchedMethod, retryableMethod.getParameterTypes());
    }

    private boolean compareParameters(Method method, Class<?>[] retryableClass) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length == 0 || (parameterTypes.length != retryableClass.length + 1)
                || !Exception.class.isAssignableFrom(parameterTypes[0])) {
            return false;
        }
        for (int i = 1; i < parameterTypes.length; ++i) {
            if (!parameterTypes[i].equals(retryableClass[i - 1])) {
                return false;
            }
        }
        return true;
    }

    private Callable<Object> buildCallable(ProceedingJoinPoint joinPoint, Object[] args) {
        return () -> {
            try {
                return joinPoint.proceed(args);
            } catch (Exception e) {
                throw e;
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        };
    }
}
