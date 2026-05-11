// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.support;

import org.fitframework.broker.DynamicRouter;
import org.fitframework.broker.Fitable;
import org.fitframework.broker.FitableMetadata;
import org.fitframework.broker.Genericable;
import org.fitframework.broker.InvocationContext;
import org.fitframework.util.ObjectUtils;
import org.fitframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 表示 {@link DynamicRouter} 的默认实现。
 *
 * @author 季聿阶
 * @since 2023-03-26
 */
public class DefaultDynamicRouter implements DynamicRouter {
    @Override
    public List<Fitable> route(Genericable genericable, InvocationContext context, Object[] args) {
        if (context.routingFilter() == null) {
            return genericable.fitables();
        }
        return context.routingFilter().filter(genericable, genericable.fitables(), args, context.filterExtensions())
                .stream()
                .filter(Objects::nonNull)
                .map(metadata -> this.cast(metadata, genericable.fitables()))
                .collect(Collectors.toList());
    }

    private Fitable cast(FitableMetadata metadata, List<Fitable> scope) {
        if (metadata instanceof Fitable) {
            return ObjectUtils.cast(metadata);
        }
        for (Fitable fitable : scope) {
            if (Objects.equals(metadata.toUniqueId(), fitable.toUniqueId())) {
                return fitable;
            }
        }
        throw new IllegalStateException(StringUtils.format(
                "Failed to cast FitableMetadata to Fitable after dynamic routing. [id={0}]",
                metadata.toUniqueId()));
    }
}
