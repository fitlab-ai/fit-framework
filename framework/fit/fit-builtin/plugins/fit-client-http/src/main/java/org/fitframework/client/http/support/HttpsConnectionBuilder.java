// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.client.http.support;

import org.fitframework.http.protocol.Protocol;

/**
 * 表示 Https 链接的构建器。
 *
 * @author 季聿阶
 * @since 2023-09-10
 */
public class HttpsConnectionBuilder extends HttpConnectionBuilder {
    @Override
    public Protocol protocol() {
        return Protocol.HTTPS;
    }
}
