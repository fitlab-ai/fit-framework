// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.support;

import org.fitframework.http.server.HttpClassicServerRequest;
import org.fitframework.http.server.HttpClassicServerResponse;
import org.fitframework.http.server.handler.SourceFetcher;

/**
 * 表示获取整个 Http 请求的 {@link SourceFetcher}。
 *
 * @author 季聿阶
 * @since 2022-08-29
 */
public class HttpClassicRequestFetcher implements SourceFetcher {
    @Override
    public Object get(HttpClassicServerRequest request, HttpClassicServerResponse response) {
        return request;
    }
}
