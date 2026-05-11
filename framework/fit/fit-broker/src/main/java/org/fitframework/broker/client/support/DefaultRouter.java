// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.client.support;

import static org.fitframework.inspection.Validation.notBlank;
import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.broker.client.Invoker;
import org.fitframework.broker.client.InvokerFactory;
import org.fitframework.broker.client.Router;

import java.lang.reflect.Method;

/**
 * {@link Router} 的默认实现。
 *
 * @author 季聿阶
 * @since 2021-06-17
 */
public class DefaultRouter implements Router {
    private final InvokerFactory invokerFactory;
    private final String genericableId;
    private final boolean isMicro;
    private final Method genericableMethod;

    DefaultRouter(InvokerFactory invokerFactory, String genericableId, boolean isMicro, Method genericableMethod) {
        this.invokerFactory = notNull(invokerFactory, "The invoker factory cannot be null.");
        this.genericableId = notBlank(genericableId, "The genericable id to route cannot be blank.");
        this.isMicro = isMicro;
        this.genericableMethod = genericableMethod;
    }

    @Override
    public Invoker route(Filter filter) {
        return this.invokerFactory.create(this.genericableId, this.isMicro, this.genericableMethod, filter);
    }
}
