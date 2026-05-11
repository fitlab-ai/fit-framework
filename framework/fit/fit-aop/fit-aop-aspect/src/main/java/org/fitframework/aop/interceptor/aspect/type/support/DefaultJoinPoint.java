// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.aspect.type.support;

import org.fitframework.aop.JoinPoint;
import org.fitframework.aop.ProceedingJoinPoint;
import org.fitframework.aop.Signature;
import org.fitframework.aop.interceptor.MethodJoinPoint;
import org.fitframework.inspection.Validation;
import org.fitframework.util.LazyLoader;
import org.fitframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * {@link JoinPoint} 和 {@link JoinPoint.StaticPart} 的 Aspect 实现。
 *
 * @author 白鹏坤
 * @since 2023-03-23
 */
public class DefaultJoinPoint implements JoinPoint, JoinPoint.StaticPart {
    private static final String EXECUTION_PATTERN = "execution({0})";

    private final MethodJoinPoint joinPoint;
    private final LazyLoader<Signature> signatureLoader;

    /**
     * 使用运行时的方法连接点来实例化 {@link DefaultJoinPoint}。
     *
     * @param joinPoint 表示运行时的方法连接点的 {@link MethodJoinPoint}。
     */
    public DefaultJoinPoint(MethodJoinPoint joinPoint) {
        this.joinPoint = Validation.notNull(joinPoint, "The method join point cannot be null.");
        this.signatureLoader = new LazyLoader<>(() -> new DefaultSignature(this.joinPoint));
    }

    @Override
    public String toString() {
        return StringUtils.format(EXECUTION_PATTERN, this.getSignature().toString());
    }

    @Override
    public String toShortString() {
        return StringUtils.format(EXECUTION_PATTERN, this.getSignature().toShortString());
    }

    @Override
    public String toLongString() {
        return StringUtils.format(EXECUTION_PATTERN, this.getSignature().toLongString());
    }

    @Override
    public Object getThis() {
        return this.joinPoint.getProxyInvocation().getTarget();
    }

    @Override
    public Object getTarget() {
        return this.joinPoint.getProxiedInvocation().getTarget();
    }

    @Override
    public Object[] getArgs() {
        return this.joinPoint.getProxiedInvocation().getArguments();
    }

    @Override
    public Method getMethod() {
        return this.joinPoint.getProxiedInvocation().getMethod();
    }

    @Override
    public Signature getSignature() {
        return this.signatureLoader.get();
    }

    @Override
    public String getKind() {
        return ProceedingJoinPoint.METHOD_EXECUTION;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public StaticPart getStaticPart() {
        return this;
    }
}
