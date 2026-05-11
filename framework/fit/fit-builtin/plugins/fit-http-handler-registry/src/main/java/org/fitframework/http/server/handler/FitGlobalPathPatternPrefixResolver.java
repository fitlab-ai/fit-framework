// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.ioc.BeanContainer;

import java.util.Optional;

/**
 * 表示从配置项 {@code 'server.http.context-path'} 中获取全局路径样式的前缀解析器。
 *
 * @author 季聿阶
 * @since 2023-07-03
 */
public class FitGlobalPathPatternPrefixResolver implements GlobalPathPatternPrefixResolver {
    private final String prefix;

    FitGlobalPathPatternPrefixResolver(BeanContainer container) {
        notNull(container, "The bean container cannot be null.");
        this.prefix = container.plugin().config().get("server.http.context-path", String.class);
    }

    @Override
    public Optional<String> resolve() {
        return Optional.ofNullable(this.prefix);
    }
}
