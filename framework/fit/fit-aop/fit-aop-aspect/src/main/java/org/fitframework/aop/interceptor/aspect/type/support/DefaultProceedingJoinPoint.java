// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.aspect.type.support;

import org.fitframework.aop.ProceedingJoinPoint;
import org.fitframework.aop.interceptor.MethodJoinPoint;
import org.fitframework.inspection.Validation;

/**
 * 通过 {@link MethodJoinPoint} 适配实现 {@link ProceedingJoinPoint}。
 *
 * @author 白鹏坤
 * @since 2023-03-23
 */
public class DefaultProceedingJoinPoint extends DefaultJoinPoint implements ProceedingJoinPoint {
    private final MethodJoinPoint joinPoint;

    /**
     * 使用运行时的方法连接点来实例化 {@link DefaultJoinPoint}。
     *
     * @param joinPoint 表示运行时的方法连接点的 {@link MethodJoinPoint}。
     */
    public DefaultProceedingJoinPoint(MethodJoinPoint joinPoint) {
        super(joinPoint);
        this.joinPoint = Validation.notNull(joinPoint, "The method join point cannot be null.");
    }

    @Override
    public Object proceed() throws Throwable {
        return this.joinPoint.proceed();
    }

    @Override
    public Object proceed(Object[] args) throws Throwable {
        return this.joinPoint.proceed(args);
    }
}
