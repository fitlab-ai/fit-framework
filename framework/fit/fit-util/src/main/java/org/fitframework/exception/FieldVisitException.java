// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.exception;

/**
 * 当访问字段失败时引发的异常。
 *
 * @author 梁济时
 * @since 2020-07-24
 */
public class FieldVisitException extends RuntimeException {
    /**
     * 使用引发异常的原因初始化 {@link FieldVisitException} 类的新实例。
     *
     * @param cause 表示引发异常的原因的 {@link Throwable}。
     */
    public FieldVisitException(Throwable cause) {
        super(cause);
    }
}
