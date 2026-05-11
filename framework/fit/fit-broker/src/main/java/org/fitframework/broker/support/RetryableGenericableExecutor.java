// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.support;

import org.fitframework.broker.Fitable;
import org.fitframework.broker.GenericableExecutor;
import org.fitframework.broker.InvocationContext;
import org.fitframework.exception.FitException;
import org.fitframework.exception.MethodInvocationException;
import org.fitframework.exception.RetryableException;
import org.fitframework.util.ExceptionUtils;
import org.fitframework.util.ObjectUtils;

import java.util.Collections;

/**
 * 表示 {@link GenericableExecutor} 的重试调用实现。
 *
 * @author 季聿阶
 * @since 2023-03-27
 */
public class RetryableGenericableExecutor extends AbstractUnicastGenericableExecutor {
    private final GenericableExecutor executor;

    RetryableGenericableExecutor(GenericableExecutor executor) {
        this.executor = executor;
    }

    @Override
    protected Object execute(Fitable fitable, InvocationContext context, Object[] args) {
        int retry = context.retry() + 1;
        RetryableException actualException;
        do {
            try {
                retry--;
                return this.executor.execute(Collections.singletonList(fitable), context, args);
            } catch (RetryableException e) {
                actualException = e;
            } catch (MethodInvocationException e) {
                Throwable cause = ExceptionUtils.getActualCause(e);
                if (cause instanceof RetryableException) {
                    actualException = ObjectUtils.cast(cause);
                } else {
                    throw FitException.wrap(cause, fitable.genericable().id(), fitable.id());
                }
            } catch (Throwable e) {
                throw FitException.wrap(e, fitable.genericable().id(), fitable.id());
            }
        } while (retry > 0);
        actualException.associateFitable(fitable.genericable().id(), fitable.id());
        throw actualException;
    }
}
