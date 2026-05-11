// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.flowable.subscriber;

import org.fitframework.flowable.FlowableException;
import org.fitframework.flowable.Subscriber;
import org.fitframework.flowable.Subscription;
import org.fitframework.inspection.Nonnull;
import org.fitframework.util.LockUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 表示阻塞等待所有结果的 {@link Subscriber 订阅者}。
 *
 * @param <T> 表示订阅者订阅的数据类型的 {@link T}。
 * @author 季聿阶
 * @since 2024-02-11
 */
public class BlockAllSubscriber<T> extends AbstractSubscriber<T> {
    private final AtomicBoolean notified = new AtomicBoolean();
    private final List<T> buffer = new ArrayList<>();
    private volatile Exception cause;
    private volatile boolean finished;
    private final Object lock = LockUtils.newSynchronizedLock();

    /**
     * 获取阻塞等待的结果队列。
     *
     * @return 表示阻塞等待的结果队列的 {@link List}{@code <}{@link T}{@code >}。
     * @throws FlowableException 当订阅数据在消费时产生任何错误时。
     */
    public List<T> getBlockedList() throws FlowableException {
        synchronized (this.lock) {
            if (!notified.get()) {
                try {
                    this.lock.wait();
                } catch (InterruptedException e) {
                    this.cancelInterruptedException(e);
                }
            }
            if (this.cause != null) {
                throw new FlowableException("Failed to block: uncaught exception occurs.", this.cause);
            } else {
                return this.buffer;
            }
        }
    }

    private void cancelInterruptedException(InterruptedException e) {
        FlowableException flowableException = new FlowableException("Failed to block: thread is interrupted.", e);
        if (this.cause != null) {
            flowableException.addSuppressed(this.cause);
        }
        throw flowableException;
    }

    @Override
    protected void onSubscribed0(@Nonnull Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    protected void consume(@Nonnull Subscription subscription, T data) {
        synchronized (this.lock) {
            if (this.finished) {
                return;
            }
            this.buffer.add(data);
        }
    }

    @Override
    protected void complete(@Nonnull Subscription subscription) {
        synchronized (this.lock) {
            if (this.finished) {
                return;
            }
            this.finished = true;
            this.notifyFinishedBlock();
        }
    }

    @Override
    protected void fail(@Nonnull Subscription subscription, Exception cause) {
        synchronized (this.lock) {
            if (this.finished) {
                return;
            }
            this.finished = true;
            this.cause = cause;
            this.notifyFinishedBlock();
        }
    }

    private void notifyFinishedBlock() {
        this.notified.set(true);
        this.lock.notifyAll();
    }
}
