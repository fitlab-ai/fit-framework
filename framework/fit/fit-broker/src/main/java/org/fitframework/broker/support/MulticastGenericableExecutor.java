// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.support;

import org.fitframework.broker.Fitable;
import org.fitframework.broker.GenericableExecutor;
import org.fitframework.broker.InvocationContext;
import org.fitframework.log.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 表示 {@link GenericableExecutor} 的多播调用实现。
 *
 * @author 季聿阶
 * @since 2023-03-28
 */
public class MulticastGenericableExecutor implements GenericableExecutor {
    private static final Logger log = Logger.get(MulticastGenericableExecutor.class);

    private final GenericableExecutor executor;

    MulticastGenericableExecutor(GenericableExecutor executor) {
        this.executor = executor;
    }

    @Override
    public Object execute(List<Fitable> fitables, InvocationContext context, Object[] args) {
        return fitables.stream().map(fitable -> {
            try {
                return this.executor.execute(Collections.singletonList(fitable), context, args);
            } catch (Throwable t) {
                log.warn("Failed to execute genericable executor while multicast, return null instead. [id={}]",
                        fitable.toUniqueId());
                return null;
            }
        }).filter(Objects::nonNull).reduce(context.accumulator()).orElse(null);
    }
}
