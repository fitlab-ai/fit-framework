// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.proxy.support.authorization;

import org.fitframework.http.client.proxy.RequestBuilder;
import org.fitframework.inspection.Nonnull;

/**
 * 鉴权信息管理的空实现。
 *
 * @author 王攀博
 * @since 2024-12-12
 */
public class EmptyAuthorization extends AbstractAuthorization {
    @Override
    public void assemble(RequestBuilder builder) {}

    @Override
    protected void setValue(String key, @Nonnull Object value) {}
}
