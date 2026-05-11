// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.entity;

import org.fitframework.http.server.HttpServerException;

/**
 * 表示消息体相关的异常。
 *
 * @author 季聿阶
 * @since 2022-07-14
 */
public class EntityException extends HttpServerException {
    /**
     * 通过异常消息来实例化 {@link EntityException}。
     *
     * @param message 表示异常消息的 {@link String}。
     */
    public EntityException(String message) {
        super(message);
    }

    /**
     * 通过异常消息和异常原因来实例化 {@link EntityException}。
     *
     * @param message 表示异常消息的 {@link String}。
     * @param cause 表示异常原因的 {@link Throwable}。
     */
    public EntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
