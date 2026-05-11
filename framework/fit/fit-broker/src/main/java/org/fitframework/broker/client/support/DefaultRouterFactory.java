// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.client.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.broker.client.InvokerFactory;
import org.fitframework.broker.client.Router;
import org.fitframework.broker.client.RouterFactory;

import java.lang.reflect.Method;

/**
 * 为 {@link RouterFactory} 提供默认实现。
 *
 * @author 季聿阶
 * @since 2021-10-26
 */
public class DefaultRouterFactory implements RouterFactory {
    private final InvokerFactory invokerFactory;

    /**
     * 使用指定的调用器工厂初始化 {@link DefaultRouterFactory} 的新实例。
     *
     * @param invokerFactory 表示调用器工厂的 {@link InvokerFactory}。
     * @throws IllegalArgumentException 当 {@code invokerFactory} 为 {@code null} 时。
     */
    public DefaultRouterFactory(InvokerFactory invokerFactory) {
        this.invokerFactory = notNull(invokerFactory, "The invoker factory cannot be null.");
    }

    @Override
    public Router create(String genericableId, boolean isMicro, Method genericableMethod) {
        return new DefaultRouter(this.invokerFactory, genericableId, isMicro, genericableMethod);
    }
}
