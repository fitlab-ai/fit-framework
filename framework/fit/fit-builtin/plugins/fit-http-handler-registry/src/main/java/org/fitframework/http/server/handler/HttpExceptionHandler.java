// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler;

import org.fitframework.http.annotation.ExceptionHandler;
import org.fitframework.http.server.HttpClassicServerRequest;
import org.fitframework.http.server.HttpClassicServerResponse;
import org.fitframework.annotation.Scope;

/**
 * 表示处理 {@link ExceptionHandler} 的处理器。
 *
 * @author 季聿阶
 * @since 2022-08-25
 */
public interface HttpExceptionHandler {
    /**
     * 获取异常返回的状态码。
     *
     * @return 表示异常返回的常态码的 {@code int}。
     */
    int statusCode();

    /**
     * 处理异常。
     *
     * @param request 表示 Http 请求的 {@link HttpClassicServerRequest}。
     * @param response 表示 Http 响应的 {@link HttpClassicServerResponse}。
     * @param cause 表示待处理的异常的 {@link Throwable}。
     * @return 表示处理异常后的返回值的 {@link Object}。
     */
    Object handle(HttpClassicServerRequest request, HttpClassicServerResponse response, Throwable cause);

    /**
     * 获取异常处理器的生效范围。
     *
     * @return 表示异常处理器的生效范围的 {@link Scope}。
     */
    Scope scope();
}
