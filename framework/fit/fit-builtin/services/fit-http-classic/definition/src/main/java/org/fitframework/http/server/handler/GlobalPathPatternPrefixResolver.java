// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler;

import org.fitframework.http.server.handler.support.GlobalPathPatternPrefixResolverComposite;

import java.util.Optional;

/**
 * 表示全局路径样式的前缀解析器。
 *
 * @author 季聿阶
 * @since 2023-06-11
 */
@FunctionalInterface
public interface GlobalPathPatternPrefixResolver {
    /**
     * 解析获取全局路径样式的前缀。
     *
     * @return 表示解析出的全局路径样式的前缀的 {@link Optional}{@code <}{@link String}{@code >}。
     */
    Optional<String> resolve();

    /**
     * 将多个全局路径样式的前缀解析器合并为一个。
     *
     * @param resolvers 表示多个全局路径样式的前缀解析器的 {@link GlobalPathPatternPrefixResolver}{@code []}。
     * @return 表示合并后的全局路径样式的前缀解析器的 {@link GlobalPathPatternPrefixResolver}。
     */
    static GlobalPathPatternPrefixResolver combine(GlobalPathPatternPrefixResolver... resolvers) {
        return new GlobalPathPatternPrefixResolverComposite(resolvers);
    }
}
