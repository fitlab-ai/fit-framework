// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.flowable.publisher;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.flowable.Publisher;
import org.fitframework.flowable.Subscriber;
import org.fitframework.flowable.Subscription;
import org.fitframework.flowable.operation.AbstractOperation;

import java.util.function.Predicate;

/**
 * 表示 {@link Publisher} 的携带数据过滤的实现。
 *
 * @param <T> 表示发布者中待过滤数据的类型。
 * @author 何天放
 * @since 2024-02-20
 */
public class FilterPublisherDecorator<T> implements Publisher<T> {
    private final Publisher<T> decorated;
    private final Predicate<T> filter;

    /**
     * 使用指定的发布者和过滤器初始化 {@link FilterPublisherDecorator} 的新实例。
     *
     * @param decorated 表示被装饰的发布者的 {@link Publisher}{@code <}{@link T}{@code >}。
     * @param filter 表示过滤器的 {@link Predicate}{@code <}{@link T}{@code >}。
     * @throws IllegalArgumentException 当 {@code decorated} 或 {@code filter} 为 {@code null} 时。
     */
    public FilterPublisherDecorator(Publisher<T> decorated, Predicate<T> filter) {
        this.decorated = notNull(decorated, "The decorated filter publisher cannot be null.");
        this.filter = notNull(filter, "The filter cannot be null.");
    }

    @Override
    public void subscribe(Subscriber<T> subscriber) {
        this.decorated.subscribe(new FilterOperation<>(this.filter, subscriber));
    }

    private static class FilterOperation<T> extends AbstractOperation<T, T> {
        private final Predicate<T> filter;

        FilterOperation(Predicate<T> filter, Subscriber<T> subscriber) {
            super(subscriber);
            this.filter = filter;
        }

        @Override
        protected void consume0(Subscription subscription, T data) {
            final boolean result;
            try {
                result = this.filter.test(data);
            } catch (Exception cause) {
                this.getPreSubscription().cancel();
                this.getNextSubscriber().fail(cause);
                return;
            }
            if (result) {
                this.getNextSubscriber().consume(data);
            } else {
                this.getPreSubscription().request(1);
            }
        }
    }
}
