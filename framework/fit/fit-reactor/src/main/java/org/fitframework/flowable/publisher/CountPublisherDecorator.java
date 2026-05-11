// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.flowable.publisher;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.flowable.Publisher;
import org.fitframework.flowable.Subscriber;
import org.fitframework.flowable.Subscription;
import org.fitframework.flowable.operation.AbstractOperation;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 表示 {@link Publisher} 的计算数据数量并将数量转换为一个响应式流的实现。
 *
 * @param <T> 表示发布者中数据类型的 {@link T}。
 * @author 黄夏露
 * @since 2024-03-04
 */
public class CountPublisherDecorator<T> implements Publisher<Long> {
    private final Publisher<T> decorated;

    /**
     * 使用指定的发布者初始化 {@link CountPublisherDecorator} 的新实例。
     *
     * @param decorated 表示被装饰的发布者的 {@link Publisher}{@code <}{@link T}{@code >}。
     * @throws IllegalArgumentException 当 {@code decorated} 为 {@code null} 时。
     */
    public CountPublisherDecorator(Publisher<T> decorated) {
        this.decorated = notNull(decorated, "The decorated count publisher cannot be null.");
    }

    @Override
    public void subscribe(Subscriber<Long> subscriber) {
        this.decorated.subscribe(new CountOperation<>(subscriber));
    }

    private static class CountOperation<T> extends AbstractOperation<T, Long> {
        private final AtomicLong count;

        CountOperation(Subscriber<Long> subscriber) {
            super(subscriber);
            this.count = new AtomicLong();
        }

        @Override
        protected void request0(long count) {
            super.request0(Long.MAX_VALUE);
        }

        @Override
        protected void consume0(Subscription subscription, T data) {
            this.count.getAndIncrement();
        }

        @Override
        protected void complete0(Subscription subscription) {
            this.getNextSubscriber().consume(this.count.get());
            super.complete0(subscription);
        }
    }
}
