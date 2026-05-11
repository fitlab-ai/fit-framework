// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.header;

import java.nio.charset.Charset;
import java.util.Optional;

/**
 * 表示消息头中关于内容类型的信息。
 *
 * @author 季聿阶
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc2616#section-14.17">RFC 2616</a>
 * @since 2022-09-04
 */
public interface ContentType extends HeaderValue {
    /**
     * 获取媒体类型。
     *
     * @return 表示媒体类型的 {@link String}。
     */
    default String mediaType() {
        return this.value();
    }

    /**
     * 获取消息内容的编码方式。
     *
     * @return 表示消息内容的编码方式的 {@link Optional}{@code <}{@link Charset}{@code >}。
     */
    Optional<Charset> charset();

    /**
     * 获取消息内容的边界。
     *
     * @return 表示消息内容的边界的 {@link Optional}{@code <}{@link String}{@code >}。
     */
    Optional<String> boundary();
}
