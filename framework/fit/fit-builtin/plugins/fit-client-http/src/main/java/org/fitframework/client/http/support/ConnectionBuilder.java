// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.client.http.support;

import org.fitframework.client.Request;
import org.fitframework.http.protocol.Protocol;

/**
 * 表示通信链接的构建器。
 *
 * @author 季聿阶
 * @since 2023-09-10
 */
public interface ConnectionBuilder {
    /**
     * 构建一个链接。
     *
     * @param request 表示请求的 {@link Request}。
     * @return 表示构建出来的链接的 {@link String}。
     */
    String buildUrl(Request request);

    /**
     * 获取构建器的类型。
     *
     * @return 表示构建器类型的 {@link Protocol}。
     */
    Protocol protocol();
}
