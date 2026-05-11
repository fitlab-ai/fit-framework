// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.example.auth;

import org.fitframework.http.client.proxy.Authorization;
import org.fitframework.http.client.proxy.auth.AuthProvider;
import org.fitframework.annotation.Component;

/**
 * 动态 Token 提供器示例。
 * <p>模拟从某个 Token 管理器获取动态 Token 的场景。
 *
 * @author 季聿阶
 * @since 2025-09-30
 */
@Component
public class DynamicTokenProvider implements AuthProvider {
    @Override
    public Authorization provide() {
        // 模拟动态获取 token
        String dynamicToken = "dynamic-token-" + System.currentTimeMillis();
        return Authorization.createBearer(dynamicToken);
    }
}