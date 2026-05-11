// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.flowable.solo;

import org.fitframework.flowable.Choir;
import org.fitframework.flowable.Publisher;
import org.fitframework.flowable.Solo;
import org.fitframework.flowable.Subscriber;
import org.fitframework.flowable.Subscription;
import org.fitframework.flowable.publisher.FilterPublisherDecorator;
import org.fitframework.flowable.publisher.FlatMapPublisherDecorator;
import org.fitframework.flowable.publisher.MapPublisherDecorator;
import org.fitframework.flowable.subscriber.BlockAllSubscriber;
import org.fitframework.flowable.subscriber.FunctionalSubscriber;
import org.fitframework.inspection.Nonnull;
import org.fitframework.inspection.Validation;
import org.fitframework.util.CollectionUtils;
import org.fitframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 表示 {@link Solo} 的抽象实现类。
 *
 * @param <T> 表示响应式流中数据类型的 {@link T}。
 * @author 季聿阶
 * @since 2024-02-11
 */
public abstract class AbstractSolo<T> implements Solo<T> {
    @Override
    public Solo<T> filter(Predicate<T> filter) {
        return Solo.fromPublisher(new FilterPublisherDecorator<>(this, filter));
    }

    @Override
    public <R> Choir<R> flatMap(Function<T, Publisher<R>> flatMapper) {
        return Choir.fromPublisher(new FlatMapPublisherDecorator<>(this, flatMapper));
    }

    @Override
    public <R> Solo<R> map(Function<T, R> mapper) {
        return Solo.fromPublisher(new MapPublisherDecorator<>(this, mapper));
    }

    @Override
    public Choir<T> toChoir() {
        return Choir.fromPublisher(this);
    }

    @Override
    public void subscribe() {
        this.subscribe(Subscriber.empty());
    }

    @Override
    public void subscribe(BiConsumer<Subscription, T> consumeAction) {
        this.subscribe(null, consumeAction, null, null);
    }

    @Override
    public void subscribe(Consumer<Subscription> onSubscribedAction, BiConsumer<Subscription, T> consumeAction,
            Consumer<Subscription> completeAction, BiConsumer<Subscription, Exception> failAction) {
        this.subscribe(Subscriber.functional(
                ObjectUtils.nullIf(onSubscribedAction, FunctionalSubscriber.DEFAULT_ON_SUBSCRIBED_SOLO_ACTION),
                ObjectUtils.nullIf(consumeAction, ObjectUtils.cast(FunctionalSubscriber.EMPTY_CONSUME_ACTION)),
                ObjectUtils.nullIf(completeAction, FunctionalSubscriber.EMPTY_COMPLETE_ACTION),
                ObjectUtils.nullIf(failAction, FunctionalSubscriber.EMPTY_FAIL_ACTION)));
    }

    @Override
    public void subscribe(Subscriber<T> subscriber) {
        this.subscribe0(ObjectUtils.getIfNull(subscriber, Subscriber::empty));
    }

    @Override
    public void subscribe(java.util.concurrent.Flow.Subscriber<? super T> subscriber) {
        Validation.notNull(subscriber, "Subscriber cannot be null.");
        // 使用现有的 Lambda subscribe 方法，适配 Flow Subscriber
        this.subscribe(
                // onSubscribedAction: 将内部 Subscription 适配为 Flow Subscription
                subscription -> subscriber.onSubscribe(new java.util.concurrent.Flow.Subscription() {
                    @Override
                    public void request(long n) {
                        subscription.request(n);
                    }

                    @Override
                    public void cancel() {
                        subscription.cancel();
                    }
                }),
                // consumeAction: 将数据传递给 Flow Subscriber
                (subscription, data) -> subscriber.onNext(data),
                // completeAction: 通知 Flow Subscriber 完成
                subscription -> subscriber.onComplete(),
                // failAction: 将异常传递给 Flow Subscriber
                (subscription, cause) -> subscriber.onError(cause)
        );
    }

    /**
     * 向发布者订阅以启动数据发送。
     *
     * @param subscriber 表示已订阅的 {@link Subscriber}{@code <}{@link T}{@code >}。
     */
    protected abstract void subscribe0(@Nonnull Subscriber<T> subscriber);

    @Override
    public Optional<T> block() {
        BlockAllSubscriber<T> subscriber = new BlockAllSubscriber<>();
        this.subscribe(subscriber);
        List<T> blockedList = subscriber.getBlockedList();
        if (CollectionUtils.isEmpty(blockedList)) {
            return Optional.empty();
        }
        return Optional.ofNullable(blockedList.get(0));
    }
}
