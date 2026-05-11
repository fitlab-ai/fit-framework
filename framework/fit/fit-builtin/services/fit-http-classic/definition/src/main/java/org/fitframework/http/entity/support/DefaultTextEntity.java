// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.entity.support;

import static org.fitframework.util.ObjectUtils.nullIf;

import org.fitframework.http.HttpMessage;
import org.fitframework.http.entity.Entity;
import org.fitframework.http.entity.TextEntity;
import org.fitframework.http.protocol.MimeType;
import org.fitframework.inspection.Nonnull;
import org.fitframework.util.StringUtils;

/**
 * {@link Entity} 的默认实现。
 *
 * @author 季聿阶
 * @since 2022-07-14
 */
public class DefaultTextEntity extends AbstractEntity implements TextEntity {
    private final String content;

    /**
     * 创建文本类型的消息体数据对象。
     *
     * @param httpMessage 表示消息体数据所属的 Http 消息的 {@link HttpMessage}。
     * @param content 表示文本数据的 {@link String}。
     */
    public DefaultTextEntity(HttpMessage httpMessage, String content) {
        super(httpMessage);
        this.content = nullIf(content, StringUtils.EMPTY);
    }

    @Override
    public String content() {
        return this.content;
    }

    @Nonnull
    @Override
    public MimeType resolvedMimeType() {
        return MimeType.TEXT_PLAIN;
    }
}
