// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.Serializers;
import org.fitframework.http.client.HttpClassicClient;
import org.fitframework.value.ValueFetcher;

/**
 * 表示 {@link HttpClassicClient} 的抽象实现类。
 *
 * @author 季聿阶
 * @since 2022-12-04
 */
public abstract class AbstractHttpClassicClient implements HttpClassicClient {
    private final Serializers serializers;
    private final ValueFetcher valueFetcher;

    /**
     * 使用指定的序列化器和值获取器初始化 {@link AbstractHttpClassicClient} 的新实例。
     *
     * @param serializers 表示序列化器的 {@link Serializers}。
     * @param valueFetcher 表示值获取器的 {@link ValueFetcher}。
     * @throws IllegalArgumentException 当 {@code serializers} 或 {@code valueFetcher} 为 {@code null} 时。
     */
    public AbstractHttpClassicClient(Serializers serializers, ValueFetcher valueFetcher) {
        this.serializers = notNull(serializers, "The serializers cannot be null.");
        this.valueFetcher = notNull(valueFetcher, "The value fetcher cannot be null.");
    }

    @Override
    public Serializers serializers() {
        return this.serializers;
    }

    @Override
    public ValueFetcher valueFetcher() {
        return this.valueFetcher;
    }
}
