// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.client.http.support;

import static org.fitframework.serialization.http.Constants.FIT_ASYNC_TASK_PATH_PATTERN;

import org.fitframework.client.Request;
import org.fitframework.http.protocol.Protocol;

/**
 * 表示 Http 链接的构建器。
 *
 * @author 季聿阶
 * @since 2023-09-10
 */
public class HttpConnectionBuilder extends AbstractConnectionBuilder {
    /**
     * 构建长轮询链接。
     *
     * @param request 表示请求的 {@link Request}。
     * @return 表示构建出来的长轮询链接的 {@link String}。
     */
    public String buildLongPollingUrl(Request request) {
        return this.buildBaseUrl(request).append(FIT_ASYNC_TASK_PATH_PATTERN).toString();
    }

    @Override
    public Protocol protocol() {
        return Protocol.HTTP;
    }
}
