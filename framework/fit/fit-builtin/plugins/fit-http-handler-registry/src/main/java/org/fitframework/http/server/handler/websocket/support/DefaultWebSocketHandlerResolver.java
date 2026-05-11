// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.websocket.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.server.HttpClassicServer;
import org.fitframework.http.server.handler.GlobalPathPatternPrefixResolver;
import org.fitframework.http.server.handler.PropertyValueMapper;
import org.fitframework.http.server.handler.PropertyValueMapperResolver;
import org.fitframework.http.server.handler.websocket.WebSocketHandlerResolver;
import org.fitframework.http.websocket.annotation.OnClose;
import org.fitframework.http.websocket.annotation.OnError;
import org.fitframework.http.websocket.annotation.OnMessage;
import org.fitframework.http.websocket.annotation.OnOpen;
import org.fitframework.http.websocket.annotation.WebSocketEndpoint;
import org.fitframework.http.websocket.server.WebSocketHandler;
import org.fitframework.http.websocket.server.support.ReflectibleWebSocketHandler;
import org.fitframework.annotation.Component;
import org.fitframework.ioc.BeanFactory;
import org.fitframework.ioc.annotation.AnnotationMetadata;
import org.fitframework.ioc.annotation.AnnotationMetadataResolver;
import org.fitframework.util.StringUtils;
import org.fitframework.util.TypeUtils;
import org.fitframework.value.PropertyValue;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 表示 {@link WebSocketHandlerResolver} 的默认实现。
 *
 * @author 季聿阶
 * @since 2023-12-09
 */
@Component
public class DefaultWebSocketHandlerResolver implements WebSocketHandlerResolver {
    private final HttpClassicServer server;

    public DefaultWebSocketHandlerResolver(HttpClassicServer server) {
        this.server = notNull(server, "The http classic server cannot be null.");
    }

    @Override
    public Optional<WebSocketHandler> resolve(BeanFactory candidate,
            GlobalPathPatternPrefixResolver pathPatternPrefixResolver, PropertyValueMapperResolver mapperResolver) {
        boolean isWebSocketEndPoint = candidate.metadata().annotations().isAnnotationPresent(WebSocketEndpoint.class);
        if (!isWebSocketEndPoint) {
            return Optional.empty();
        }
        String globalPrefix = pathPatternPrefixResolver.resolve().orElse(StringUtils.EMPTY);
        WebSocketEndpoint endpoint = candidate.metadata().annotations().getAnnotation(WebSocketEndpoint.class);
        String path = globalPrefix + endpoint.path();
        WebSocketHandler.Info.Builder infoBuilder =
                WebSocketHandler.Info.custom().httpServer(this.server).pathPattern(path).target(candidate.get());
        Method[] methods = TypeUtils.toClass(candidate.metadata().type()).getDeclaredMethods();
        AnnotationMetadataResolver annotationResolver = candidate.metadata().runtime().resolverOfAnnotations();
        for (Method method : methods) {
            AnnotationMetadata annotations = annotationResolver.resolve(method);
            if (annotations.isAnnotationPresent(OnOpen.class)) {
                infoBuilder.openMethod(method).openMethodMappers(this.resolveMappers(method, mapperResolver));
            } else if (annotations.isAnnotationPresent(OnMessage.class)) {
                infoBuilder.messageMethod(method).messageMethodMappers(this.resolveMappers(method, mapperResolver));
            } else if (annotations.isAnnotationPresent(OnError.class)) {
                infoBuilder.errorMethod(method).errorMethodMappers(this.resolveMappers(method, mapperResolver));
            } else if (annotations.isAnnotationPresent(OnClose.class)) {
                infoBuilder.closeMethod(method).closeMethodMappers(this.resolveMappers(method, mapperResolver));
            }
        }
        return Optional.of(new ReflectibleWebSocketHandler(infoBuilder.build()));
    }

    private List<PropertyValueMapper> resolveMappers(Method method, PropertyValueMapperResolver mapperResolver) {
        return Stream.of(method.getParameters())
                .map(PropertyValue::createParameterValue)
                .map(mapperResolver::resolve)
                .map(optional -> optional.orElse(PropertyValueMapper.empty()))
                .collect(Collectors.toList());
    }
}
