// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler;

import org.fitframework.ioc.BeanContainer;

/**
 * 表示 {@link HttpResponseStatusResolver} 的提供者。
 *
 * @author 季聿阶
 * @since 2023-01-11
 */
@FunctionalInterface
public interface HttpResponseStatusResolverSupplier {
    /**
     * 从指定容器中获取 Http 响应状态的解析器。
     *
     * @param container 表示指定容器的 {@link BeanContainer}。
     * @return 表示获取到的 Http 响应状态的解析器的 {@link HttpResponseStatusResolver}。
     */
    HttpResponseStatusResolver get(BeanContainer container);
}
