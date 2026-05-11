// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.proxy.support.authorization;

import static org.fitframework.util.ObjectUtils.cast;

import org.fitframework.http.client.proxy.RequestBuilder;
import org.fitframework.inspection.Nonnull;
import org.fitframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 表示基础类型鉴权的实现。
 *
 * @author 王攀博
 * @since 2024-11-26
 */
public class BasicAuthorization extends AbstractAuthorization {
    /**
     * 表示鉴权的用户名。
     */
    static final String AUTH_USER_NAME = "username";

    /**
     * 表示鉴权的密码。
     */
    static final String AUTH_USER_PWD = "password";

    private static final String AUTH_HEADER_KEY = "Authorization";

    private String username;
    private String password;

    /**
     * 使用指定的用户名和密码初始化 {@link BasicAuthorization} 的新实例。
     *
     * @param username 表示用户名的 {@link String}。
     * @param password 表示密码的 {@link String}。
     */
    public BasicAuthorization(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void setValue(String key, @Nonnull Object value) {
        if (StringUtils.equals(key, AUTH_USER_NAME) && value instanceof String) {
            this.username = cast(value);
        } else if (StringUtils.equals(key, AUTH_USER_PWD) && value instanceof String) {
            this.password = cast(value);
        } else {
            throw new IllegalArgumentException(StringUtils.format("Invalid key. [key={0}]", key));
        }
    }

    @Override
    public void assemble(RequestBuilder builder) {
        String auth = this.username + ":" + this.password;
        auth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
        builder.header(AUTH_HEADER_KEY, BasicAuthorizationFactory.TYPE + " " + auth);
    }
}
