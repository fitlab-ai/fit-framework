// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.protocol.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.protocol.HttpResponse;
import org.fitframework.http.protocol.HttpResponseStatus;

/**
 * 表示 {@link HttpResponse} 的默认实现。
 *
 * @author 季聿阶
 * @since 2023-11-28
 */
public class DefaultHttpResponse implements HttpResponse {
    private final HttpResponseStatus status;
    private final Object entity;

    public DefaultHttpResponse(HttpResponseStatus status, Object entity) {
        this.status = notNull(status, "The http response status cannot be null.");
        this.entity = entity;
    }

    @Override
    public HttpResponseStatus status() {
        return this.status;
    }

    @Override
    public Object entity() {
        return this.entity;
    }
}
