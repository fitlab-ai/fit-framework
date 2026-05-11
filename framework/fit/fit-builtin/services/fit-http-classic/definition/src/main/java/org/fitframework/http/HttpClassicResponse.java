// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http;

/**
 * 表示经典的 Http 响应。
 *
 * @author 季聿阶
 * @since 2022-07-07
 */
public interface HttpClassicResponse extends HttpMessage {
    /**
     * 获取 Http 响应的状态码。
     *
     * @return 表示 Http 响应的状态码的 {@code int}。
     */
    int statusCode();

    /**
     * 获取 Http 响应的状态信息。
     *
     * @return 表示 Http 响应的状态信息的 {@link String}。
     */
    String reasonPhrase();
}
