// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.flowable.solo;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.flowable.Publisher;
import org.fitframework.flowable.Solo;
import org.fitframework.flowable.Subscriber;
import org.fitframework.flowable.Subscription;
import org.fitframework.flowable.subscription.AbstractSubscription;
import org.fitframework.flowable.util.worker.Worker;
import org.fitframework.flowable.util.worker.WorkerObserver;
import org.fitframework.inspection.Nonnull;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 表示 {@link Solo} 的指定 {@link Publisher} 的适配。
 *
 * @param <T> 表示响应式流中数据类型的 {@link T}。
 * @author 季聿阶
 * @since 2024-02-09
 */
public class PublisherSoloAdapter<T> extends AbstractSolo<T> {
    private final Publisher<T> publisher;

    /**
     * 使用指定的发布者初始化 {@link PublisherSoloAdapter} 的新实例。
     *
     * @param publisher 表示发布者的 {@link Publisher}{@code <}{@link T}{@code >}。
     * @throws IllegalArgumentException 当 {@code publisher} 为 {@code null} 时。
     */
    public PublisherSoloAdapter(Publisher<T> publisher) {
        this.publisher = notNull(publisher, "The publisher cannot be null.");
    }

    @Override
    protected void subscribe0(@Nonnull Subscriber<T> subscriber) {
        // 需要将原先的 Publisher 封装为一个新的 Subscription 从而达到控制元素发送数量和正常终结信号发送时机的目的。
        new PublisherAdapterSubscription<>(subscriber, this.publisher);
    }

    /**
     * 表示在将 {@link Publisher} 转换为 {@link Solo} 时辅助发送响应式流中元素的工具。
     * <p>能够限制向 {@link Subscriber} 发送元素的数量，并在恰当的时机发送正常终结信号。</p>
     *
     * @param <T> 表示响应式流中元素类型的 {@link T}。
     * @author 何天放
     * @since 2024-05-06
     */
    private static class PublisherAdapterSubscription<T> extends AbstractSubscription implements WorkerObserver<T> {
        private final Subscriber<T> subscriber;

        private final AtomicBoolean requested = new AtomicBoolean();
        private final AtomicBoolean failed = new AtomicBoolean();
        private final AtomicBoolean completed = new AtomicBoolean();
        private final Worker<T> worker;

        /**
         * 通过待转换的 {@link Publisher} 和其进行订阅的 {@link Subscriber} 创建辅助发送响应式流中元素的工具。
         *
         * @param subscriber 表示对于 {@link Publisher} 进行订阅的 {@link Subscriber}。
         * @param publisher 表示被 {@link Subscriber} 订阅的 {@link Publisher}。
         */
        public PublisherAdapterSubscription(Subscriber<T> subscriber, Publisher<T> publisher) {
            this.subscriber = notNull(subscriber, "The subscriber cannot be null.");
            this.worker = Worker.create(this, notNull(publisher, "The publisher cannot be null."), 0);
            this.worker.run();
        }

        @Override
        public void request0(long count) {
            if (this.requested.compareAndSet(false, true)) {
                this.worker.request(1);
            }
        }

        @Override
        public void cancel0() {
            this.worker.cancel();
        }

        @Override
        public void onWorkerSubscribed(Subscription subscription) {
            this.subscriber.onSubscribed(this);
        }

        @Override
        public void onWorkerConsumed(T data, long id) {
            this.subscriber.consume(data);
            if (this.completed.compareAndSet(false, true)) {
                this.subscriber.complete();
            }
        }

        @Override
        public void onWorkerFailed(Exception cause) {
            if (this.failed.compareAndSet(false, true)) {
                this.subscriber.fail(cause);
            }
        }

        @Override
        public void onWorkerCompleted() {
            if (this.completed.compareAndSet(false, true)) {
                this.subscriber.complete();
            }
        }
    }
}
