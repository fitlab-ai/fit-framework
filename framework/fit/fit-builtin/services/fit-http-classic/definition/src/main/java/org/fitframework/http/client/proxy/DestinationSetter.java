// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.proxy;

/**
 * 表示数据目标设置器。
 *
 * @author 季聿阶
 * @since 2024-05-11
 */
public interface DestinationSetter {
    /**
     * 将数据设置进 Http 请求。
     *
     * @param requestBuilder 表示 Http 请求建造者的 {@link RequestBuilder}。
     * @param value 表示待设置的值的 {@link Object}。
     */
    void set(RequestBuilder requestBuilder, Object value);
}
