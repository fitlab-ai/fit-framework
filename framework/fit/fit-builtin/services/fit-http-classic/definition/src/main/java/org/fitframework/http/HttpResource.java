// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http;

import org.fitframework.value.ValueFetcher;

/**
 * 表示 Http 的资源。
 *
 * @author 季聿阶
 * @since 2022-11-22
 */
public interface HttpResource {
    /**
     * 获取序列化器的集合。
     *
     * @return 表示序列化器集合的 {@link Serializers}。
     */
    Serializers serializers();

    /**
     * 获取求值器。
     *
     * @return 表示求值器的 {@link ValueFetcher}。
     */
    ValueFetcher valueFetcher();
}
