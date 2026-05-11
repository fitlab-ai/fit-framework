// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.async;

import org.fitframework.aop.interceptor.MethodMatcher;

/**
 * 表示异步执行方法的方法匹配器的工厂。
 *
 * @author 季聿阶
 * @since 2022-11-13
 */
public interface AsyncMethodMatcherFactory {
    /**
     * 根据异步执行线程池名字来创建一个异步执行方法的方法匹配器。
     *
     * @param asyncExecutorName 表示异步执行线程池名字的 {@link String}。
     * @return 表示异步执行方法的方法匹配器的 {@link MethodMatcher}。
     */
    MethodMatcher create(String asyncExecutorName);
}
