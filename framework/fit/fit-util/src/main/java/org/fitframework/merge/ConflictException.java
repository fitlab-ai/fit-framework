// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.merge;

/**
 * 表示冲突的异常。
 *
 * @author 季聿阶
 * @since 2022-07-30
 */
public class ConflictException extends RuntimeException {
    /**
     * 通过异常信息来实例化 {@link ConflictException}。
     *
     * @param message 表示异常信息的 {@link String}。
     */
    public ConflictException(String message) {
        super(message);
    }

    /**
     * 通过异常信息和异常原因来实例化 {@link ConflictException}。
     *
     * @param message 表示异常信息的 {@link String}。
     * @param cause 表示异常原因的 {@link Throwable}。
     */
    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
