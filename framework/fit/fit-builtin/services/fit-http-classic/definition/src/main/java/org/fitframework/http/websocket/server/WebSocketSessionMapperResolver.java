// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.websocket.server;

import org.fitframework.http.server.handler.PropertyValueMapper;
import org.fitframework.http.server.handler.PropertyValueMapperResolver;
import org.fitframework.http.websocket.Session;
import org.fitframework.value.PropertyValue;

import java.util.Optional;

/**
 * 表示 {@link WebSocketSessionMapper} 的解析器。
 *
 * @author 季聿阶
 * @since 2023-12-10
 */
public class WebSocketSessionMapperResolver implements PropertyValueMapperResolver {
    @Override
    public Optional<PropertyValueMapper> resolve(PropertyValue propertyValue) {
        if (propertyValue.getParameterizedType() != Session.class) {
            return Optional.empty();
        }
        return Optional.of(new WebSocketSessionMapper());
    }
}
