// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.conf.runtime;

import java.util.Map;

/**
 * 表示运行时 {@code 'application.'} 前缀的配置项。
 *
 * @author 季聿阶
 * @since 2023-07-08
 */
public interface ApplicationConfig {
    /**
     * 获取 {@code 'application.name'} 的配置项。
     *
     * @return 表示 {@code 'application.name'} 的配置项的 {@link String}。
     */
    String name();

    /**
     * 获取 {@code 'application.extensions'} 的配置项。
     *
     * @return 表示 {@code 'application.extensions'} 的配置项的 {@link Map}{@code <}{@link String}{@code ,
     * }{@link Object}{@code >}。
     */
    Map<String, Object> extensions();

    /**
     * 获取 {@code 'application.extensions'} 的配置项的可视化信息。
     *
     * @return 表示 {@code 'application.extensions'} 的配置项的可视化信息的 {@link Map}{@code <}{@link String}{@code ,
     * }{@link String}{@code >}。
     */
    Map<String, String> visualExtensions();
}
