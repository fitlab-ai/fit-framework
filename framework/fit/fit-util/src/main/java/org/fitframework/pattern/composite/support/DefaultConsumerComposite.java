// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.pattern.composite.support;

import org.fitframework.inspection.Validation;
import org.fitframework.pattern.composite.ConsumerComposite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * 为 {@link ConsumerComposite} 提供默认实现。
 *
 * @author 梁济时
 * @author 季聿阶
 * @since 2020-10-15
 */
public class DefaultConsumerComposite<T> implements ConsumerComposite<T> {
    private final List<Consumer<T>> consumers;

    /**
     * 初始化 {@link DefaultConsumerComposite} 的新实例。
     */
    public DefaultConsumerComposite() {
        this.consumers = new ArrayList<>();
    }

    @Override
    public void add(Consumer<T> consumer) {
        this.consumers.add(consumer);
    }

    @Override
    public void addAll(Collection<Consumer<T>> consumers) {
        Validation.notNull(consumers, "The consumers to add to consumer composite cannot be null.");
        this.consumers.addAll(consumers);
    }

    @Override
    public void remove(Consumer<T> consumer) {
        this.consumers.remove(consumer);
    }

    @Override
    public void removeAll(Collection<Consumer<T>> consumers) {
        Validation.notNull(consumers, "The consumers to remove from consumer composite cannot be null.");
        this.consumers.removeAll(consumers);
    }

    @Override
    public List<Consumer<T>> getConsumers() {
        return Collections.unmodifiableList(this.consumers);
    }
}
