// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.entity;

import org.fitframework.http.HttpMessage;
import org.fitframework.http.entity.support.DefaultTextEventStreamEntity;
import org.fitframework.flowable.Choir;

/**
 * 表示文本事件流的消息体数据。
 *
 * @author 易文渊
 * @since 2024-07-16
 */
public interface TextEventStreamEntity extends Entity {
    /**
     * 获取文本事件流。
     *
     * @return 表示文本事件流的 {@link Choir}{@code <}{@link TextEvent}{@code >}。
     */
    Choir<TextEvent> stream();

    /**
     * 通过数据流来创建响应式流数据。
     *
     * @param httpMessage 表示 Http 消息的 {@link HttpMessage}。
     * @param stream 表示数据流的 {@link Choir}{@code <?>}。
     * @return 表示创建的文本事件流的消息体数据的 {@link TextEventStreamEntity}。
     * @throws IllegalArgumentException 当 {@code httpMessage} 为 {@code null} 时。
     */
    static TextEventStreamEntity create(HttpMessage httpMessage, Choir<?> stream) {
        return new DefaultTextEventStreamEntity(httpMessage, stream);
    }
}