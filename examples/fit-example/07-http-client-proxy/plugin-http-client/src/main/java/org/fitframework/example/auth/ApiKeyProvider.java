// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.example.auth;

import org.fitframework.http.client.proxy.Authorization;
import org.fitframework.http.client.proxy.auth.AuthProvider;
import org.fitframework.http.server.handler.Source;
import org.fitframework.annotation.Component;

/**
 * API Key 提供器示例。
 * <p>提供动态的 API Key 鉴权。
 *
 * @author 季聿阶
 * @since 2025-09-30
 */
@Component
public class ApiKeyProvider implements AuthProvider {
    @Override
    public Authorization provide() {
        // 模拟从配置或环境变量获取 API Key
        String apiKey = "api-key-" + System.currentTimeMillis();
        return Authorization.createApiKey("X-API-Key", apiKey, Source.HEADER);
    }
}