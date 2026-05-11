// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler;

import org.fitframework.http.annotation.DocumentIgnored;
import org.fitframework.http.annotation.RequestMapping;
import org.fitframework.http.protocol.HttpRequestMethod;
import org.fitframework.annotation.Component;

/**
 * 表示全局默认的 {@link HttpRequestMethod#OPTIONS} 处理方法。
 *
 * @author 季聿阶
 * @since 2023-07-21
 */
@Component
public class OptionsHttpHandler {
    /**
     * 处理 Option 请求。
     */
    @DocumentIgnored
    @RequestMapping(method = HttpRequestMethod.OPTIONS, path = "/**")
    public void handleOptions() {}
}
