// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.plugin;

import java.net.URL;

/**
 * 为 {@link Plugin} 提供解析程序。
 *
 * @author 梁济时
 * @since 2022-06-06
 */
@FunctionalInterface
public interface PluginResolver {
    /**
     * 解析插件。
     *
     * @param parent 表示父插件的 {@link Plugin}。
     * @param location 表示待解析的插件所在的位置的 {@link URL}。
     * @return 表示已解析的插件的 {@link Plugin}。
     */
    Plugin resolve(Plugin parent, URL location);
}
