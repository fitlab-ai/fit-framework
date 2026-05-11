// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.support;

import org.fitframework.broker.Fitable;
import org.fitframework.broker.GenericableExecutor;
import org.fitframework.broker.InvocationContext;
import org.fitframework.inspection.Validation;

import java.util.List;

/**
 * 表示 {@link GenericableExecutor} 的单播调用抽象实现类。
 *
 * @author 季聿阶
 * @since 2023-03-27
 */
public abstract class AbstractUnicastGenericableExecutor implements GenericableExecutor {
    @Override
    public final Object execute(List<Fitable> fitables, InvocationContext context, Object[] args) {
        Validation.notNull(fitables, "The fitables cannot be null.");
        Validation.equals(fitables.size(), 1, "Too more fitables.");
        return this.execute(fitables.get(0), context, args);
    }

    /**
     * 执行指定的服务实现。
     *
     * @param fitable 表示指定服务实现的 {@link Fitable}。
     * @param context 表示调用上下文的 {@link InvocationContext}。
     * @param args 表示调用参数列表的 {@link Object}{@code []}。
     * @return 表示调用结果的 {@link Object}。
     */
    protected abstract Object execute(Fitable fitable, InvocationContext context, Object[] args);
}
