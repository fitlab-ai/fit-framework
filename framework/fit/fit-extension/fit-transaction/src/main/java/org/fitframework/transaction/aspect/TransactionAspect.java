// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.transaction.aspect;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.annotation.Component;
import org.fitframework.aop.MethodSignature;
import org.fitframework.aop.ProceedingJoinPoint;
import org.fitframework.aop.annotation.Around;
import org.fitframework.aop.annotation.Aspect;
import org.fitframework.ioc.BeanContainer;
import org.fitframework.ioc.annotation.AnnotationMetadata;
import org.fitframework.transaction.Transaction;
import org.fitframework.transaction.TransactionManager;
import org.fitframework.transaction.TransactionMetadata;
import org.fitframework.transaction.Transactional;
import org.fitframework.util.ObjectUtils;

import java.util.Locale;

/**
 * 为事务提供切面能力。
 *
 * @author 梁济时
 * @since 2022-08-30
 */
@Aspect
@Component
public class TransactionAspect {
    private static final String DEFAULT_NAME_PREFIX = "Transactional";

    private final BeanContainer container;
    private final TransactionManager manager;

    /**
     * 使用事务管理程序初始化 {@link TransactionAspect} 类的新实例。
     *
     * @param container 表示所属的 Bean 容器的 {@link BeanContainer}。
     * @param transactionManager 表示事物管理器的 {@link TransactionManager}。
     * @throws IllegalArgumentException 当 {@code transactionManager} 为 {@code null} 时。
     */
    public TransactionAspect(BeanContainer container, TransactionManager transactionManager) {
        this.container = notNull(container, "The owning bean container cannot be null.");
        this.manager = notNull(transactionManager, "The transaction manager cannot be null.");
    }

    /**
     * 表示事务注解生效的切面方法。
     *
     * @param joinPoint 表示切面中切点的 {@link ProceedingJoinPoint}。
     * @return 表示切面结束之后的返回值的 {@link Object}。
     * @throws Throwable 当切面执行过程中发生的异常时。
     */
    @Around("@annotation(org.fitframework.transaction.Transactional)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = ObjectUtils.cast(joinPoint.getSignature());
        AnnotationMetadata annotations =
                this.container.runtime().resolverOfAnnotations().resolve(signature.getMethod());
        Transactional annotation = annotations.getAnnotation(Transactional.class);
        TransactionMetadata metadata = TransactionMetadata.custom()
                .name(nameOf(annotation))
                .propagation(annotation.propagation())
                .isolation(annotation.isolation())
                .timeout(annotation.timeout())
                .readonly(annotation.readonly())
                .build();
        Transaction transaction = this.manager.begin(metadata);
        try {
            Object result = joinPoint.proceed();
            transaction.commit();
            return result;
        } catch (Throwable cause) {
            transaction.rollback();
            throw cause;
        }
    }

    private static String nameOf(Transactional annotation) {
        String name = annotation.name().trim();
        if (name.isEmpty()) {
            name = String.format(Locale.ROOT,
                    "%s-%s-%016x",
                    DEFAULT_NAME_PREFIX,
                    Thread.currentThread().getName(),
                    System.currentTimeMillis());
        }
        return name;
    }
}
