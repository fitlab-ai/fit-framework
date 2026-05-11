// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.support;

import static org.fitframework.inspection.Validation.notBlank;

import org.fitframework.http.Cookie;
import org.fitframework.http.server.HttpClassicServerRequest;
import org.fitframework.http.server.HttpClassicServerResponse;
import org.fitframework.http.server.handler.RequestMappingException;
import org.fitframework.http.server.handler.SourceFetcher;
import org.fitframework.http.server.handler.exception.RequestParamFetchException;
import org.fitframework.util.StringUtils;

/**
 * 表示从 Cookie 中获取值的 {@link SourceFetcher}。
 *
 * @author 季聿阶
 * @since 2022-08-28
 */
public class CookieFetcher extends AbstractSourceFetcher {
    private final String cookieName;

    /**
     * 通过 Cookie 的名字来实例化 {@link CookieFetcher}。
     *
     * @param cookieName 表示 Cookie 名字的 {@link String}。
     * @throws IllegalArgumentException 当 {@code cookieName} 为 {@code null} 或空白字符串时。
     */
    public CookieFetcher(String cookieName) {
        super(false, null);
        this.cookieName =
                notBlank(cookieName, () -> new RequestParamFetchException("The cookie name cannot be blank."));
    }

    /**
     * 通过 Cookie 的参数元数据来实例化 {@link CookieFetcher}。
     *
     * @param paramValue 表示参数元数据的 {@link ParamValue}。
     * @throws IllegalArgumentException 当 {@code cookieName} 为 {@code null} 或空白字符串时。
     */
    public CookieFetcher(ParamValue paramValue) {
        super(paramValue.required(), paramValue.defaultValue());
        this.cookieName =
                notBlank(paramValue.name(), () -> new RequestParamFetchException("The cookie name cannot be blank."));
    }

    @Override
    public Object get(HttpClassicServerRequest request, HttpClassicServerResponse response) {
        try {
            return this.resolveValue(request.cookies().get(this.cookieName).map(Cookie::value).orElse(null));
        } catch (RequestMappingException e) {
            throw new RequestMappingException(StringUtils.format("Invalid cookie param. [cookieName={0}]",
                    this.cookieName), e);
        }
    }
}
