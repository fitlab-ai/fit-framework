// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.support;

import org.fitframework.annotation.Order;
import org.fitframework.aop.interceptor.AdviceMethodInterceptor;
import org.fitframework.aop.interceptor.MethodInterceptor;
import org.fitframework.aop.interceptor.OrderResolver;
import org.fitframework.ioc.annotation.AnnotationMetadata;
import org.fitframework.ioc.annotation.AnnotationMetadataResolver;
import org.fitframework.ioc.annotation.AnnotationMetadataResolvers;
import org.fitframework.util.ObjectUtils;

/**
 * {@link Order} 优先级解析器。
 * <ol>
 *     <li>首先从方法的 {@link Order} 注解中获取优先级，存在则返回解析结果。</li>
 *     <li>如果方法上没有注解，则从类上的 {@link Order} 注解获取优先级。若没有则解析失败。</li>
 * </ol>
 *
 * @author 詹高扬
 * @since 2022-08-01
 */
public class AnnotationOrderResolver implements OrderResolver {
    private final AnnotationMetadataResolver resolver = AnnotationMetadataResolvers.create();

    @Override
    public Result resolve(MethodInterceptor methodInterceptor) {
        if (!(methodInterceptor instanceof AdviceMethodInterceptor)) {
            return Result.builder().success(false).build();
        }
        AdviceMethodInterceptor adviceMethodInterceptor = ObjectUtils.cast(methodInterceptor);
        AnnotationMetadata annotations = this.resolver.resolve(adviceMethodInterceptor.getAdvisorMethod());
        if (annotations.isAnnotationPresent(Order.class)) {
            return Result.builder().success(true).order(annotations.getAnnotation(Order.class).value()).build();
        }
        Object targetClass = adviceMethodInterceptor.getAdvisorTarget();
        if (targetClass == null) {
            return Result.builder().success(false).build();
        }
        annotations = this.resolver.resolve(targetClass.getClass());
        if (annotations.isAnnotationPresent(Order.class)) {
            return Result.builder().success(true).order(annotations.getAnnotation(Order.class).value()).build();
        }
        return Result.builder().success(false).build();
    }
}
