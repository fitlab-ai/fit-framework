// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.plugin;

import java.net.URL;

/**
 * 为公共JAR提供注册入口。
 *
 * @author 梁济时
 * @since 2022-06-20
 */
public interface SharedJarRegistry {
    /**
     * 注册公共JAR。
     *
     * @param jar 表示公共JAR的URL的 {@link URL}。
     */
    void register(URL jar);
}
