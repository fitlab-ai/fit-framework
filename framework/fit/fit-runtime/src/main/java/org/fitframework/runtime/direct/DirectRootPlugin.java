// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.runtime.direct;

import org.fitframework.conf.Config;
import org.fitframework.inspection.Nonnull;
import org.fitframework.model.Version;
import org.fitframework.plugin.Plugin;
import org.fitframework.plugin.PluginCategory;
import org.fitframework.plugin.PluginKey;
import org.fitframework.plugin.PluginMetadata;
import org.fitframework.plugin.support.DefaultPluginKey;
import org.fitframework.plugin.support.DefaultPluginMetadata;
import org.fitframework.runtime.FitRuntime;
import org.fitframework.runtime.support.AbstractRootPlugin;
import org.fitframework.util.ObjectUtils;

import java.net.URL;

/**
 * 为根插件提供直接调用启动的实现。
 *
 * @author 梁济时
 * @since 2023-02-07
 */
final class DirectRootPlugin extends AbstractRootPlugin {
    private static final String APP_GROUP_CONFIG_KEY = "application.group";
    private static final String APP_NAME_CONFIG_KEY = "application.name";
    private static final String APP_VERSION_CONFIG_KEY = "application.version";

    private final FitRuntime runtime;
    private final PluginMetadata metadata;

    DirectRootPlugin(FitRuntime runtime) {
        this.runtime = runtime;
        this.metadata = buildPluginMetadata(runtime.config(), runtime.location());
    }

    private static PluginMetadata buildPluginMetadata(Config config, URL location) {
        String group = config.get(APP_GROUP_CONFIG_KEY, String.class);
        String name = config.get(APP_NAME_CONFIG_KEY, String.class);
        String version = config.get(APP_VERSION_CONFIG_KEY, String.class);
        // fit-runtime 包在 lib 目录中，lib 目录的上级目录为应用程序根目录
        PluginKey pluginKey = new DefaultPluginKey(ObjectUtils.nullIf(group, "direct.group"),
                ObjectUtils.nullIf(name, "direct"),
                Version.parse(ObjectUtils.nullIf(version, "0.0.1-direct")));
        return new DefaultPluginMetadata(pluginKey, location, PluginCategory.SYSTEM, Integer.MIN_VALUE);
    }

    @Nonnull
    @Override
    public PluginMetadata metadata() {
        return this.metadata;
    }

    @Nonnull
    @Override
    public ClassLoader pluginClassLoader() {
        return DirectRootPlugin.class.getClassLoader();
    }

    @Nonnull
    @Override
    public FitRuntime runtime() {
        return this.runtime;
    }

    @Override
    protected void loadPlugins() {
        // 插件都被加载到一起，没有单独的加载插件逻辑
    }

    @Override
    public Plugin loadPlugin(URL plugin) {
        return this;
    }

    @Override
    public Plugin unloadPlugin(URL plugin) {
        return this;
    }
}
