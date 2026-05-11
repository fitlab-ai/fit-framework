// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.header;

import org.fitframework.http.Cookie;
import org.fitframework.http.support.DefaultCookieCollection;

/**
 * 表示 Http 中的可读可写的 Cookie 集合。
 *
 * @author 季聿阶
 * @since 2022-07-07
 */
public interface ConfigurableCookieCollection extends CookieCollection {
    /**
     * 添加一个指定的 {@link Cookie}。
     *
     * @param cookie 表示待添加的 {@link Cookie}。
     */
    void add(Cookie cookie);

    /**
     * 创建一个新的可读可写的 Cookie 集合。
     *
     * @return 表示创建出来的可读可写的 Cookie 集合的 {@link ConfigurableCookieCollection}。
     */
    static ConfigurableCookieCollection create() {
        return new DefaultCookieCollection();
    }
}
