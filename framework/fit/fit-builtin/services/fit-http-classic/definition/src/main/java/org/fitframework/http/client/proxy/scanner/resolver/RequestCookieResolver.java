// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.proxy.scanner.resolver;

import org.fitframework.http.annotation.RequestCookie;
import org.fitframework.http.client.proxy.scanner.ParamResolver;
import org.fitframework.http.client.proxy.support.setter.CookieDestinationSetter;
import org.fitframework.http.client.proxy.support.setter.DestinationSetterInfo;

/**
 * Resolves the {@link RequestCookie} annotation into a destination setter information object.
 * This class implements the {@link ParamResolver} interface and is responsible for parsing
 * the {@link RequestCookie} annotation and converting it into a {@link DestinationSetterInfo}
 * object that can be used to set cookies on HTTP request objects.
 *
 * @author 王攀博
 * @since 2025-02-10
 */
public class RequestCookieResolver implements ParamResolver<RequestCookie> {
    @Override
    public DestinationSetterInfo resolve(RequestCookie annotation, String jsonPath) {
        return new DestinationSetterInfo(new CookieDestinationSetter(annotation.name()), jsonPath);
    }
}