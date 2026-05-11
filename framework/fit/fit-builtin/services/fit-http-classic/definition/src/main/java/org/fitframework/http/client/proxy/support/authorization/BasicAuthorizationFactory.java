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
 * Basic 鉴权工厂的实现。
 *
 * @author 王攀博
 * @since 2024-12-10
 */
public class BasicAuthorizationFactory implements AuthorizationFactory {
    /**
     * 表示鉴权的类型。
     */
    static final String TYPE = "Basic";

    @Override
    public Authorization create(Map<String, Object> authorization) {
        Map<String, Object> actual = nullIf(authorization, Collections.emptyMap());
        return Authorization.createBasic(this.getUsername(actual), this.getPassword(actual));
    }

    private String getUsername(Map<String, Object> authorization) {
        Object username = authorization.get(BasicAuthorization.AUTH_USER_NAME);
        if (username instanceof String) {
            return cast(username);
        }
        return null;
    }

    private String getPassword(Map<String, Object> authorization) {
        Object password = authorization.get(BasicAuthorization.AUTH_USER_PWD);
        if (password instanceof String) {
            return cast(password);
        }
        return null;
    }
}
