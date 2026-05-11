// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.websocket;

import org.fitframework.http.server.handler.GlobalPathPatternPrefixResolver;
import org.fitframework.http.server.handler.PropertyValueMapperResolver;
import org.fitframework.http.websocket.server.WebSocketHandler;
import org.fitframework.ioc.BeanFactory;

import java.util.Optional;

/**
 * 表示 {@link WebSocketHandler} 的解析器。
 *
 * @author 季聿阶
 * @since 2023-12-09
 */
public interface WebSocketHandlerResolver {
    /**
     * 解析指定的 WebSocket 处理器所在的 Bean 的候选者，返回解析后的 WebSocket 处理器。
     *
     * @param candidate 表示 WebSocket 处理器的所在 Bean 的候选者的 {@link BeanFactory}。
     * @param pathPatternPrefixResolver 表示全局路径样式的前缀解析器的 {@link GlobalPathPatternPrefixResolver}。
     * @param mapperResolver 表示解析方法参数的映射的解析器的 {@link PropertyValueMapperResolver}。
     * @return 表示解析后的 Http 处理器组的 {@link Optional}{@code <}{@link WebSocketHandler}{@code >}。
     */
    Optional<WebSocketHandler> resolve(BeanFactory candidate, GlobalPathPatternPrefixResolver pathPatternPrefixResolver,
            PropertyValueMapperResolver mapperResolver);
}
