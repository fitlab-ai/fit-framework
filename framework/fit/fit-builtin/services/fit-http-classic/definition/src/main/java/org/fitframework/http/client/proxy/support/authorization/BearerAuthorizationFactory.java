// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.proxy.support.authorization;

import static org.fitframework.util.ObjectUtils.cast;
import static org.fitframework.util.ObjectUtils.nullIf;

import org.fitframework.http.client.proxy.Authorization;
import org.fitframework.http.client.proxy.AuthorizationFactory;

import java.util.Collections;
import java.util.Map;

/**
 * Bearer 鉴权工厂的实现。
 *
 * @author 王攀博
 * @since 2024-12-10
 */
public class BearerAuthorizationFactory implements AuthorizationFactory {
    /**
     * 表示鉴权的类型。
     */
    static final String TYPE = "Bearer";

    @Override
    public Authorization create(Map<String, Object> authorization) {
        Map<String, Object> actual = nullIf(authorization, Collections.emptyMap());
        return Authorization.createBearer(this.getToken(actual));
    }

    private String getToken(Map<String, Object> authorization) {
        Object token = authorization.get(BearerAuthorization.AUTH_TOKEN);
        if (token instanceof String) {
            return cast(token);
        }
        return null;
    }
}
