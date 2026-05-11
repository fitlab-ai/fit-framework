// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.example;

import org.fitframework.annotation.Component;
import org.fitframework.annotation.Scope;
import org.fitframework.aop.ProceedingJoinPoint;
import org.fitframework.aop.annotation.Around;
import org.fitframework.aop.annotation.Aspect;
import org.fitframework.log.Logger;

/**
 * 全局日志切面。
 */
@Aspect(scope = Scope.GLOBAL)
@Component
public class LoggingAspect {
    private static final Logger logger = Logger.get(LoggingAspect.class);

    @Around("@annotation(org.fitframework.http.annotation.GetMapping)")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        // 获取方法信息
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        // 记录方法开始日志
        logger.info("===> {}.{}() 开始执行", className, methodName);
        try {
            // 执行目标方法
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            // 记录方法结束日志
            logger.info("<=== {}.{}() 执行成功 | 耗时: {}ms", className, methodName, executionTime);
            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            logger.error("<=== {}.{}() 执行失败 | 耗时: {}ms | 异常: {}",
                    className,
                    methodName,
                    executionTime,
                    e.getMessage(),
                    e);
            throw e;
        }
    }
}
