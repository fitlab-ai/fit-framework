// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.entity.support;

import org.fitframework.http.HttpMessage;
import org.fitframework.http.entity.TextEvent;
import org.fitframework.http.entity.TextEventStreamEntity;
import org.fitframework.http.protocol.MimeType;
import org.fitframework.flowable.Choir;
import org.fitframework.inspection.Nonnull;
import org.fitframework.util.ObjectUtils;

/**
 * 表示 {@link TextEventStreamEntity} 的默认实现。
 *
 * @author 易文渊
 * @since 2024-07-15
 */
public class DefaultTextEventStreamEntity extends AbstractEntity implements TextEventStreamEntity {
    private final Choir<TextEvent> stream;

    /**
     * 创建文本事件流消息体数据的默认实现对象。
     *
     * @param httpMessage 表示消息体数据所属的 Http 消息的 {@link HttpMessage}。
     * @param stream 表示数据流的 {@link Choir}{@code <}{@link Object}{@code >}。
     */
    public DefaultTextEventStreamEntity(HttpMessage httpMessage, Choir<?> stream) {
        super(httpMessage);
        this.stream = stream == null
                ? Choir.empty()
                : stream.map(data -> data instanceof TextEvent
                        ? ObjectUtils.cast(data)
                        : TextEvent.custom(data).build());
    }

    @Nonnull
    @Override
    public MimeType resolvedMimeType() {
        return MimeType.TEXT_EVENT_STREAM;
    }

    @Override
    public Choir<TextEvent> stream() {
        return this.stream;
    }
}