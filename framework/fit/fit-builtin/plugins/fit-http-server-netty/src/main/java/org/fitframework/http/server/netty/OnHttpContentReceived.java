// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.netty;

import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.LastHttpContent;

import java.io.IOException;

/**
 * 表示收到 Http 请求的内容的事件。
 *
 * @author 季聿阶
 * @since 2022-07-14
 */
public interface OnHttpContentReceived {
    /**
     * 当收到 Http 请求内容时触发的回调。
     *
     * @param content 表示 Http 请求内容的 {@link HttpContent}。
     * @throws IOException 当发生 I/O 异常时。
     */
    void receiveHttpContent(HttpContent content) throws IOException;

    /**
     * 当收到最后一个 Http 请求内容时触发的回调。
     *
     * @param content 表示最后一个 Http 请求内容的 {@link LastHttpContent}。
     * @throws IOException 当发生 I/O 异常时。
     */
    void receiveLastHttpContent(LastHttpContent content) throws IOException;
}
