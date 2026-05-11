// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.support;

import static org.fitframework.http.protocol.MessageHeaderNames.COOKIE;
import static org.fitframework.http.protocol.MessageHeaderNames.HOST;
import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.HttpClassicRequest;
import org.fitframework.http.HttpResource;
import org.fitframework.http.protocol.HttpRequestMethod;
import org.fitframework.http.protocol.MessageHeaderNames;
import org.fitframework.http.protocol.MessageHeaders;
import org.fitframework.http.protocol.QueryCollection;
import org.fitframework.http.protocol.RequestLine;
import org.fitframework.http.util.HttpUtils;

/**
 * 表示 {@link HttpClassicRequest} 的抽象实现类。
 *
 * @author 季聿阶
 * @since 2022-11-23
 */
public abstract class AbstractHttpClassicRequest extends AbstractHttpMessage implements HttpClassicRequest {
    private static final String COOKIE_DELIMITER = ";";

    private final RequestLine startLine;
    private final MessageHeaders headers;

    /**
     * 创建经典的 Http 请求对象。
     *
     * @param httpResource 表示 Http 的资源的 {@link HttpResource}。
     * @param startLine 表示 Http 请求的起始行的 {@link RequestLine}。
     * @param headers 表示只读的 Http 消息头集合的 {@link MessageHeaders}。
     */
    public AbstractHttpClassicRequest(HttpResource httpResource, RequestLine startLine, MessageHeaders headers) {
        super(httpResource, startLine, headers);
        this.startLine = notNull(startLine, "The request line cannot be null.");
        this.headers = notNull(headers, "The message headers cannot be null.");
        String actualCookie = String.join(COOKIE_DELIMITER, this.headers.all(COOKIE));
        HttpUtils.parseCookies(actualCookie).forEach(this.cookies()::add);
    }

    @Override
    public HttpRequestMethod method() {
        return this.headers.first(MessageHeaderNames.X_HTTP_METHOD_OVERRIDE)
                .map(HttpRequestMethod::from)
                .orElse(this.startLine.method());
    }

    @Override
    public String requestUri() {
        return this.startLine.requestUri();
    }

    @Override
    public String host() {
        return this.headers.first(HOST).orElse(null);
    }

    @Override
    public String path() {
        return this.startLine.requestUri();
    }

    @Override
    public QueryCollection queries() {
        return this.startLine.queries();
    }
}
