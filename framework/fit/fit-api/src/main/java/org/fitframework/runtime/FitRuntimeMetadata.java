// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.runtime;

import org.fitframework.conf.Config;
import org.fitframework.io.virtualization.VirtualDirectory;
import org.fitframework.plugin.PluginMetadata;

import java.util.List;

/**
 * 为 {@link FitRuntime} 提供元数据。
 *
 * @author 梁济时
 * @since 2022-08-12
 */
public interface FitRuntimeMetadata extends PluginMetadata {
    /**
     * 获取启动配置。
     *
     * @return 表示启动配置的 {@link Config}。
     */
    Config startupConfig();

    /**
     * 获取运行环境的入口类。
     *
     * @return 表示入口类的 {@link Class}。
     */
    Class<?> entryClass();

    /**
     * 获取插件仓的位置列表。
     *
     * @return 表示插件仓位置列表的 {@link List}{@code <}{@link VirtualDirectory}{@code >}。
     */
    List<VirtualDirectory> pluginRepos();

    /**
     * 获取待加载的插件名称的列表。
     *
     * @return 表示插件名称列表的 {@link List}{@code <}{@link String}{@code >}。
     */
    List<String> pluginCandidates();
}
