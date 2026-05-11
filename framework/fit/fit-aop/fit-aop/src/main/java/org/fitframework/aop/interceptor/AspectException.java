// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor;

/**
 * 表示 AOP 的基本异常。
 *
 * @author 季聿阶
 * @since 2022-05-10
 */
public class AspectException extends RuntimeException {
    /**
     * 使用指定异常实例化 {@link AspectException}。
     *
     * @param throwable 表示指定异常的 {@link Throwable}。
     */
    public AspectException(Throwable throwable) {
        super(throwable);
    }
}
