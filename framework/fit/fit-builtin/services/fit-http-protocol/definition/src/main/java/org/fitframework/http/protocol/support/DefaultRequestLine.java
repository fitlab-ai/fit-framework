// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.protocol.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.protocol.HttpRequestMethod;
import org.fitframework.http.protocol.HttpVersion;
import org.fitframework.http.protocol.QueryCollection;
import org.fitframework.http.protocol.RequestLine;

/**
 * 表示 {@link RequestLine} 的默认实现。
 *
 * @author 季聿阶
 * @since 2022-11-27
 */
public class DefaultRequestLine implements RequestLine {
    private final HttpVersion httpVersion;
    private final HttpRequestMethod method;
    private final String requestUri;
    private final QueryCollection queries;

    public DefaultRequestLine(HttpVersion httpVersion, HttpRequestMethod method, String requestUri,
            QueryCollection queries) {
        this.httpVersion = notNull(httpVersion, "The http version cannot be null.");
        this.method = notNull(method, "The request method cannot be null.");
        this.requestUri = notNull(requestUri, "The request uri cannot be null.");
        this.queries = notNull(queries, "The query collection cannot be null.");
    }

    @Override
    public HttpRequestMethod method() {
        return this.method;
    }

    @Override
    public String requestUri() {
        return this.requestUri;
    }

    @Override
    public QueryCollection queries() {
        return this.queries;
    }

    @Override
    public HttpVersion httpVersion() {
        return this.httpVersion;
    }

    @Override
    public String toString() {
        return this.method + " " + this.requestUri + " " + this.httpVersion;
    }
}
