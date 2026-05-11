// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker;

import java.util.List;

/**
 * 表示负载均衡器。
 *
 * @author 季聿阶
 * @since 2023-03-28
 */
public interface LoadBalancer {
    /**
     * 对指定的服务实现进行负载均衡。
     *
     * @param fitable 表示指定服务实现的 {@link Fitable}。
     * @param context 表示调用上下文的 {@link InvocationContext}。
     * @param args 表示调用参数列表的 {@link Object}{@code []}。
     * @return 表示负载均衡后的地址列表的 {@link List}{@code <}{@link Target}{@code >}。
     */
    List<Target> balance(Fitable fitable, InvocationContext context, Object[] args);
}
