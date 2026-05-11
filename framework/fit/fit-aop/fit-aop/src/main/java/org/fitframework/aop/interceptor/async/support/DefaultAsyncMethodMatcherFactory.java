// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.async.support;

import org.fitframework.aop.interceptor.MethodMatcher;
import org.fitframework.aop.interceptor.async.AsyncMethodMatcher;
import org.fitframework.aop.interceptor.async.AsyncMethodMatcherFactory;

/**
 * 表示 {@link AsyncMethodMatcherFactory} 的默认实现。
 *
 * @author 季聿阶
 * @since 2022-11-13
 */
public class DefaultAsyncMethodMatcherFactory implements AsyncMethodMatcherFactory {
    @Override
    public MethodMatcher create(String asyncExecutorName) {
        return new AsyncMethodMatcher(asyncExecutorName);
    }
}
