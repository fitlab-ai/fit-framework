// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.flowable.choir;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.flowable.Publisher;
import org.fitframework.flowable.Subscriber;
import org.fitframework.inspection.Nonnull;

/**
 * 表示 {@link org.fitframework.flowable.Choir} 的指定 {@link Publisher} 的适配。
 *
 * @param <T> 表示响应式流中数据类型的 {@link T}。
 * @author 季聿阶
 * @since 2024-02-09
 */
public class PublisherChoirAdapter<T> extends AbstractChoir<T> {
    private final Publisher<T> publisher;

    /**
     * 使用指定的发布者初始化 {@link PublisherChoirAdapter} 的新实例。
     *
     * @param publisher 表示发布者的 {@link Publisher}{@code <}{@link T}{@code >}。
     * @throws IllegalArgumentException 当 {@code publisher} 为 {@code null} 时。
     */
    public PublisherChoirAdapter(Publisher<T> publisher) {
        this.publisher = notNull(publisher, "The publisher cannot be null.");
    }

    @Override
    protected void subscribe0(@Nonnull Subscriber<T> subscriber) {
        this.publisher.subscribe(subscriber);
    }
}
