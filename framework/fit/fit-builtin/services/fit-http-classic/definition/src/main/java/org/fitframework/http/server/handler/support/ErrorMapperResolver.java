// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.support;

import static org.fitframework.util.ObjectUtils.cast;

import org.fitframework.http.server.handler.PropertyValueMapper;
import org.fitframework.http.server.handler.PropertyValueMapperResolver;
import org.fitframework.value.PropertyValue;

import java.util.Optional;

/**
 * 表示 {@link ErrorMapper} 的解析器。
 *
 * @author 季聿阶
 * @since 2023-12-11
 */
public class ErrorMapperResolver implements PropertyValueMapperResolver {
    @Override
    public Optional<PropertyValueMapper> resolve(PropertyValue propertyValue) {
        if (Throwable.class.isAssignableFrom(propertyValue.getType())) {
            Class<Throwable> errorClass = cast(propertyValue.getType());
            return Optional.of(new ErrorMapper(errorClass));
        }
        return Optional.empty();
    }
}
