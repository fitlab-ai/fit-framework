// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.support;

import org.fitframework.http.protocol.HttpResponseStatus;
import org.fitframework.http.server.handler.HttpResponseStatusResolver;
import org.fitframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 表示 {@link HttpResponseStatusResolver} 的组合器。
 *
 * @author 季聿阶
 * @since 2023-01-11
 */
public class HttpResponseStatusResolverComposite implements HttpResponseStatusResolver {
    private final List<HttpResponseStatusResolver> resolvers;

    /**
     * 创建 Http 响应状态解析器的组合器对象。
     *
     * @param resolvers 表示需要组合的解析器的 {@link HttpResponseStatusResolver}。
     */
    public HttpResponseStatusResolverComposite(HttpResponseStatusResolver... resolvers) {
        HttpResponseStatusResolver[] actualResolvers =
                ObjectUtils.getIfNull(resolvers, () -> new HttpResponseStatusResolver[0]);
        this.resolvers = Stream.of(actualResolvers).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public Optional<HttpResponseStatus> resolve(Method method) {
        for (HttpResponseStatusResolver resolver : this.resolvers) {
            Optional<HttpResponseStatus> resolved = resolver.resolve(method);
            if (resolved.isPresent()) {
                return resolved;
            }
        }
        return Optional.empty();
    }
}
