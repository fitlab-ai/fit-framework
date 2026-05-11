// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler;

import org.fitframework.http.server.HttpHandlerGroup;
import org.fitframework.http.server.HttpServerFilter;
import org.fitframework.ioc.BeanFactory;

import java.util.List;
import java.util.Optional;

/**
 * 表示 Http 处理器的解析器。
 *
 * @author 季聿阶
 * @since 2022-08-30
 */
public interface HttpHandlerResolver {
    /**
     * 解析指定的 Http 处理器所在的 Bean 的候选者，返回解析后的方法与 Http 处理器的映射。
     *
     * @param candidate 表示 Http 处理器的所在 Bean 的候选者的 {@link BeanFactory}。
     * @param preFilters 表示解析后的 Http 处理器的前置过滤器列表的 {@link List}{@code <}{@link HttpServerFilter}{@code >}。
     * @param pathPatternPrefixResolver 表示全局路径样式的前缀解析器的 {@link GlobalPathPatternPrefixResolver}。
     * @param mapperResolver 表示解析方法参数的映射的解析器的 {@link PropertyValueMapperResolver}。
     * @param metadataResolver 表示解析方法参数的元数据的解析器的 {@link PropertyValueMetadataResolver}。
     * @param responseStatusResolver 表示解析返回值状态的解析器的 {@link HttpResponseStatusResolver}。
     * @return 表示解析后的 Http 处理器组的 {@link Optional}{@code <}{@link HttpHandlerGroup}{@code >}。
     */
    Optional<HttpHandlerGroup> resolve(BeanFactory candidate, List<HttpServerFilter> preFilters,
            GlobalPathPatternPrefixResolver pathPatternPrefixResolver, PropertyValueMapperResolver mapperResolver,
            PropertyValueMetadataResolver metadataResolver, HttpResponseStatusResolver responseStatusResolver);
}
