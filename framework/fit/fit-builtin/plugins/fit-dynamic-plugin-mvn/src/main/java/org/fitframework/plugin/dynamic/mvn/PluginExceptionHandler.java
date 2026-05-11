// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.plugin.dynamic.mvn;

import org.fitframework.http.annotation.ExceptionHandler;
import org.fitframework.http.annotation.ResponseStatus;
import org.fitframework.http.protocol.HttpResponseStatus;
import org.fitframework.annotation.Component;
import org.fitframework.util.MapBuilder;

import java.util.Map;

/**
 * 表示动态插件控制器的统一异常处理器。
 *
 * @author 季聿阶
 * @since 2023-09-17
 */
@Component
public class PluginExceptionHandler {
    /**
     * 处理当发生参数异常时的情况。
     *
     * @param cause 表示参数异常的 {@link IllegalArgumentException}。
     * @return 表示处理完毕后的返回值的 {@link Map}{@code <}{@link String}{@code , }{@link Object}{@code >}。
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpResponseStatus.BAD_REQUEST)
    public Map<String, Object> handleIllegalArgumentException(IllegalArgumentException cause) {
        return MapBuilder.<String, Object>get().put("error", cause.getMessage()).build();
    }

    /**
     * 处理当发生未识别异常时的情况。
     *
     * @param cause 表示未识别异常的 {@link Exception}。
     * @return 表示处理完毕后的返回值的 {@link Map}{@code <}{@link String}{@code , }{@link Object}{@code >}。
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleException(Exception cause) {
        return MapBuilder.<String, Object>get().put("error", cause.getMessage()).build();
    }
}
