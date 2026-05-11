// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.proxy.support.authorization;

import org.fitframework.http.client.proxy.Authorization;
import org.fitframework.http.client.proxy.AuthorizationFactory;

import java.util.Map;

/**
 * 鉴权信息管理空实现的工厂。
 *
 * @author 王攀博
 * @since 2024-12-12
 */
public class EmptyAuthorizationFactory implements AuthorizationFactory {
    /**
     * 表示鉴权的类型。
     */
    public static final String TYPE = "NoAuth";

    @Override
    public Authorization create(Map<String, Object> authorization) {
        return Authorization.createEmpty();
    }
}
