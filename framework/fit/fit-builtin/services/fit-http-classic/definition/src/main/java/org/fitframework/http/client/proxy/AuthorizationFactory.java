// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.proxy;

import java.util.Map;

/**
 * 表示鉴权工厂。
 *
 * @author 王攀博
 * @since 2024-12-10
 */
public interface AuthorizationFactory {
    /**
     * 根据鉴权信息创建不同类型的鉴权信息管理。
     *
     * @param authorization 表示鉴权信息的 {@link Map}{@code <}{@link String}{@code ,}{@link Object}{@code >}。
     * @return 表示鉴权信息的 {@link Authorization}。
     */
    Authorization create(Map<String, Object> authorization);
}
