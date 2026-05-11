// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.websocket.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示 WebSocket 的端点。
 *
 * @author 季聿阶
 * @since 2023-12-10
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface WebSocketEndpoint {
    /**
     * 获取 WebSocket 端点的路径。
     *
     * @return 表示 WebSocket 端点的路径的 {@link String}。
     */
    String path();
}
