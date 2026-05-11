// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.server.HttpClassicServerRequest;
import org.fitframework.http.server.HttpClassicServerResponse;
import org.fitframework.http.server.handler.PropertyValueMapper;

import java.util.Map;

/**
 * 表示错误参数的映射器。
 *
 * @author 季聿阶
 * @since 2023-12-11
 */
public class ErrorMapper implements PropertyValueMapper {
    /** 表示在自定义上下文中错误的主键。 */
    public static final String ERROR_KEY = "FIT-Error";

    private final Class<Throwable> errorClass;

    /**
     * 使用指定的错误类初始化 {@link ErrorMapper} 的新实例。
     *
     * @param errorClass 表示错误类的 {@link Class}{@code <}{@link Throwable}{@code >}。
     * @throws IllegalArgumentException 当 {@code errorClass} 为 {@code null} 时。
     */
    public ErrorMapper(Class<Throwable> errorClass) {
        this.errorClass = notNull(errorClass, "The error class cannot be null.");
    }

    @Override
    public Object map(HttpClassicServerRequest request, HttpClassicServerResponse response,
            Map<String, Object> context) {
        if (context == null) {
            return null;
        }
        Object error = context.get(ERROR_KEY);
        if (error == null) {
            return null;
        }
        if (this.errorClass.isAssignableFrom(error.getClass())) {
            return error;
        }
        return null;
    }
}
