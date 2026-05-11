// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.header.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.header.ContentType;
import org.fitframework.http.header.HeaderValue;
import org.fitframework.log.Logger;
import org.fitframework.util.ExceptionUtils;

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Optional;

/**
 * 表示 {@link ContentType} 的默认实现。
 *
 * @author 季聿阶
 * @since 2022-09-04
 */
public class DefaultContentType extends DefaultHeaderValue implements ContentType {
    /** 表示字符集参数的 {@link String}。 */
    public static final String CHARSET = "charset";

    private static final Logger log = Logger.get(DefaultContentType.class);
    private static final String BOUNDARY = "boundary";

    /**
     * 使用指定的消息头初始化 {@link DefaultContentType} 的新实例。
     *
     * @param headerValue 表示消息头的 {@link HeaderValue}。
     * @throws IllegalArgumentException 当 {@code headerValue} 为 {@code null} 时。
     */
    public DefaultContentType(HeaderValue headerValue) {
        super(notNull(headerValue, "The header value cannot be null.").value(), headerValue.parameters());
    }

    @Override
    public Optional<Charset> charset() {
        return this.parameters().get(CHARSET).map(this::forName);
    }

    private Charset forName(String contentCharset) {
        try {
            return Charset.forName(contentCharset);
        } catch (IllegalCharsetNameException | UnsupportedCharsetException e) {
            log.warn("Illegal content charset. [charset={}, reason={}]", contentCharset, ExceptionUtils.getReason(e));
            return null;
        }
    }

    @Override
    public Optional<String> boundary() {
        return this.parameters().get(BOUNDARY);
    }
}
