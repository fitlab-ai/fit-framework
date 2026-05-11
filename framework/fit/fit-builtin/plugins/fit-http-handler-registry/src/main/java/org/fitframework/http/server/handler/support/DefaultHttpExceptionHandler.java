// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.server.HttpClassicServerRequest;
import org.fitframework.http.server.HttpClassicServerResponse;
import org.fitframework.http.server.handler.HttpExceptionHandler;
import org.fitframework.http.server.handler.PropertyValueMapper;
import org.fitframework.annotation.Scope;
import org.fitframework.util.MapBuilder;
import org.fitframework.util.ObjectUtils;
import org.fitframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 表示 {@link HttpExceptionHandler} 的默认实现。
 *
 * @author 季聿阶
 * @since 2022-08-25
 */
public class DefaultHttpExceptionHandler implements HttpExceptionHandler {
    private final Object target;
    private final Method method;
    private final int statusCode;
    private final List<PropertyValueMapper> propertyValueMappers;
    private final Scope scope;

    public DefaultHttpExceptionHandler(Object target, Method method, int statusCode,
            List<PropertyValueMapper> propertyValueMappers, Scope scope) {
        this.target = notNull(target, "The bean of exception handler cannot be null.");
        this.method = notNull(method, "The method of exception handler cannot be null.");
        this.statusCode = statusCode;
        this.propertyValueMappers = ObjectUtils.getIfNull(propertyValueMappers, Collections::emptyList);
        this.scope = scope;
    }

    @Override
    public int statusCode() {
        return this.statusCode;
    }

    @Override
    public Scope scope() {
        return this.scope;
    }

    @Override
    public Object handle(HttpClassicServerRequest request, HttpClassicServerResponse response, Throwable cause) {
        Map<String, Object> context = MapBuilder.<String, Object>get().put(ErrorMapper.ERROR_KEY, cause).build();
        Object[] args = this.propertyValueMappers.stream()
                .map(httpMapper -> httpMapper.map(request, response, context))
                .toArray();
        return ReflectionUtils.invoke(this.target, this.method, args);
    }
}
