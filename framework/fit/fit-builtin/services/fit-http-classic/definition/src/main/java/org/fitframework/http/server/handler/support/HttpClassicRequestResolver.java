// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.support;

import org.fitframework.http.server.HttpClassicServerRequest;
import org.fitframework.http.server.handler.PropertyValueMapper;
import org.fitframework.http.server.handler.PropertyValueMapperResolver;
import org.fitframework.value.PropertyValue;

import java.util.Optional;

/**
 * 表示解析 {@link HttpClassicServerRequest} 对象参数的 {@link PropertyValueMapperResolver}。
 *
 * @author 季聿阶
 * @since 2022-08-29
 */
public class HttpClassicRequestResolver implements PropertyValueMapperResolver {
    @Override
    public Optional<PropertyValueMapper> resolve(PropertyValue propertyValue) {
        if (propertyValue.getParameterizedType() != HttpClassicServerRequest.class) {
            return Optional.empty();
        }
        UniqueSourcePropertyValueMapper mapper =
                new UniqueSourcePropertyValueMapper(new HttpClassicRequestFetcher(), false);
        return Optional.of(mapper);
    }
}
