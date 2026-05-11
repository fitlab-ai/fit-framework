// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.transaction;

/**
 * 当创建事务失败时引发的异常。
 *
 * @author 梁济时
 * @since 2022-08-24
 */
public class TransactionCreationException extends TransactionException {
    /**
     * 使用异常信息初始化 {@link TransactionCreationException} 类的新实例。
     *
     * @param message 表示异常信息的 {@link String}。
     */
    public TransactionCreationException(String message) {
        super(message);
    }

    /**
     * 使用引发异常的原因初始化 {@link TransactionCreationException} 类的新实例。
     *
     * @param cause 表示引发异常的原因的 {@link Throwable}。
     */
    public TransactionCreationException(Throwable cause) {
        super(cause);
    }

    /**
     * 使用异常信息和引发异常的原因初始化 {@link TransactionCreationException} 类的新实例。
     *
     * @param message 表示异常信息的 {@link String}。
     * @param cause 表示引发异常的原因的 {@link Throwable}。
     */
    public TransactionCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
