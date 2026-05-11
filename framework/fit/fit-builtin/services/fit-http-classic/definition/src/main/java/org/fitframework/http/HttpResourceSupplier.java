// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http;

/**
 * {@link HttpResource} 的提供者。
 *
 * @author 季聿阶
 * @since 2022-08-19
 */
public interface HttpResourceSupplier {
    /**
     * 获取 Http 的资源。
     *
     * @return 表示 Http 资源的 {@link HttpResource}。
     */
    HttpResource httpResource();
}
