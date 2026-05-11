// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.service;

import org.fitframework.service.entity.TokenInfo;
import org.fitframework.annotation.Genericable;

import java.util.List;

/**
 * 注册中心用于鉴权的服务。
 *
 * @author 李金绪
 * @since 2024-07-24
 */
public interface TokenService {
    /**
     * 表示申请令牌的方法。
     *
     * @param accessKey 表示访问密钥的 {@link String}。
     * @param timestamp 表示时间戳的 {@link String}。
     * @param signature 表示签名的 {@link String}。
     * @return 表示令牌信息的 {@link List}{@code <}{@link TokenInfo}{@code >}。
     */
    @Genericable(id = "matata.registry.secure-access.apply-token")
    List<TokenInfo> applyToken(String accessKey, String timestamp, String signature);

    /**
     * 表示刷新令牌的方法。
     *
     * @param refreshToken 表示刷新令牌的 {@link String}。
     * @return 表示令牌信息的 {@link List}{@code <}{@link TokenInfo}{@code >}。
     */
    @Genericable(id = "matata.registry.secure-access.refresh-token")
    List<TokenInfo> refreshToken(String refreshToken);
}
