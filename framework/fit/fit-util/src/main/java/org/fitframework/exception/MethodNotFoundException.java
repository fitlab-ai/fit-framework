// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.exception;

/**
 * 当没有指定的方法时引发的异常。
 *
 * @author 梁济时
 * @since 2020-07-24
 */
public class MethodNotFoundException extends RuntimeException {
    /**
     * 使用引发异常的原因初始化 {@link MethodNotFoundException} 类的新实例。
     *
     * @param cause 表示引发异常的原因的 {@link Throwable}。
     */
    public MethodNotFoundException(Throwable cause) {
        super(cause);
    }
}
