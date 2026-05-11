// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.proxy.support.authorization;

import static org.fitframework.util.ObjectUtils.cast;
import static org.fitframework.util.ObjectUtils.nullIf;

import org.fitframework.http.client.proxy.Authorization;
import org.fitframework.http.client.proxy.AuthorizationFactory;
import org.fitframework.http.server.handler.Source;
import org.fitframework.util.StringUtils;

import java.util.Collections;
import java.util.Map;

/**
 * Api Key 鉴权工厂的实现。
 *
 * @author 王攀博
 * @since 2024-12-10
 */
public class ApiKeyAuthorizationFactory implements AuthorizationFactory {
    /**
     * 表示鉴权的类型。
     */
    public static final String TYPE = "ApiKey";
    private static final String AUTH_HTTP_SOURCE_KEY = "httpSource";

    @Override
    public Authorization create(Map<String, Object> authorization) {
        Map<String, Object> actual = nullIf(authorization, Collections.emptyMap());
        return Authorization.createApiKey(this.getKey(actual), this.getValue(actual), this.getHttpSource(actual));
    }

    private String getKey(Map<String, Object> authorization) {
        Object authKey = authorization.get(ApiKeyAuthorization.AUTH_KEY);
        if (authKey instanceof String) {
            return cast(authKey);
        }
        return null;
    }

    private String getValue(Map<String, Object> authorization) {
        Object authValue = authorization.get(ApiKeyAuthorization.AUTH_VALUE);
        if (authValue instanceof String) {
            return cast(authValue);
        }
        return null;
    }

    private Source getHttpSource(Map<String, Object> authorization) {
        Object httpSource = authorization.get(AUTH_HTTP_SOURCE_KEY);
        if (!(httpSource instanceof String)) {
            return Source.HEADER;
        }
        String source = cast(httpSource);
        if (StringUtils.equalsIgnoreCase(source, Source.QUERY.name())) {
            return Source.QUERY;
        }
        return Source.HEADER;
    }
}
