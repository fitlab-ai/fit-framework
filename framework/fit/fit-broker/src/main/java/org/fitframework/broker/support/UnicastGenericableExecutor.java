// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.support;

import org.fitframework.broker.Fitable;
import org.fitframework.broker.GenericableExecutor;
import org.fitframework.broker.InvocationContext;
import org.fitframework.exception.FitException;

/**
 * 表示 {@link GenericableExecutor} 的单播调用实现。
 *
 * @author 季聿阶
 * @since 2023-03-27
 */
public class UnicastGenericableExecutor extends AbstractUnicastGenericableExecutor {
    @Override
    protected Object execute(Fitable fitable, InvocationContext context, Object[] args) {
        try {
            return fitable.execute(context, args);
        } catch (Throwable e) {
            throw FitException.wrap(e, fitable.genericable().id(), fitable.id());
        }
    }
}
