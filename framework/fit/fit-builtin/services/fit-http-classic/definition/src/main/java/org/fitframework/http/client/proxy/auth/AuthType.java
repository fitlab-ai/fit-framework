// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.proxy.auth;

/**
 * 表示 HTTP 请求的鉴权类型枚举。
 * <p>定义了框架支持的各种鉴权方式。</p>
 *
 * @author 季聿阶
 * @since 2025-09-30
 */
public enum AuthType {
    /**
     * Bearer Token 鉴权。
     * <p>通常用于 JWT Token 等场景，会在 Authorization 头中添加 "Bearer {token}"。</p>
     */
    BEARER,

    /**
     * Basic 鉴权。
     * <p>使用用户名和密码进行基础认证，会在 Authorization 头中添加 "Basic {base64(username:password)}"。</p>
     */
    BASIC,

    /**
     * API Key 鉴权。
     * <p>使用 API 密钥进行认证，可以放在 Header、Query 参数或 Cookie 中。</p>
     */
    API_KEY,

    /**
     * 自定义鉴权。
     * <p>通过 AuthProvider 提供自定义的鉴权逻辑，支持复杂的鉴权场景。</p>
     */
    CUSTOM
}