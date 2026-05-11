// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.proxy.support.authorization;

import org.fitframework.http.client.proxy.Authorization;
import org.fitframework.inspection.Nonnull;

/**
 * 鉴权信息管理的抽象接口。
 *
 * @author 王攀博
 * @since 2024-11-26
 */
public abstract class AbstractAuthorization implements Authorization {
    @Override
    public void set(String key, Object value) {
        if (value == null) {
            return;
        }
        this.setValue(key, value);
    }

    /**
     * 表示设置值到鉴权信息中。
     *
     * @param key 表示鉴权信息的参数键值 {@link String}。
     * @param value 表示鉴权信息值的 {@link Object}。
     */
    protected abstract void setValue(String key, @Nonnull Object value);
}
