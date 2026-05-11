// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.aspect.interceptor.inject;

import org.fitframework.aop.interceptor.MethodJoinPoint;
import org.fitframework.aop.interceptor.MethodPointcut;

/**
 * 方法参数的注入信息。
 *
 * @author 季聿阶
 * @since 2022-05-19
 */
public class ParameterInjection {
    private final MethodPointcut pointcut;
    private final MethodJoinPoint joinPoint;

    /**
     * 使用方法定义的切点集合、运行时的连接点信息实例化 {@link ParameterInjection}。
     *
     * @param pointcut 表示方法定义的切点集合的 {@link MethodPointcut}。
     * @param joinPoint 表示运行时的连接点信息的 {@link MethodJoinPoint}。
     */
    public ParameterInjection(MethodPointcut pointcut, MethodJoinPoint joinPoint) {
        this.pointcut = pointcut;
        this.joinPoint = joinPoint;
    }

    /**
     * 获取方法定义的切点集合。
     *
     * @return 表示方法定义的切点集合的 {@link MethodPointcut}。
     */
    public MethodPointcut getPointcut() {
        return this.pointcut;
    }

    /**
     * 获取运行时的连接点信息。
     *
     * @return 表示运行时的连接点信息的 {@link MethodJoinPoint}。
     */
    public MethodJoinPoint getJoinPoint() {
        return this.joinPoint;
    }
}
