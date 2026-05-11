// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.plugin.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.plugin.Plugin;
import org.fitframework.plugin.PluginMetadata;
import org.fitframework.protocol.jar.Jar;
import org.fitframework.runtime.FitRuntime;
import org.fitframework.util.StringUtils;

/**
 * 为插件提供 JAR 格式的实现。
 *
 * @author 梁济时
 * @since 2023-01-29
 */
public final class JarPlugin extends AbstractPlugin implements Plugin {
    private static final String FIT_ROOT_DIRECTORY_NAME = "FIT-INF/";
    private static final String SHARED_DIRECTORY_NAME = FIT_ROOT_DIRECTORY_NAME + "shared";
    private static final String LIB_DIRECTORY_NAME = FIT_ROOT_DIRECTORY_NAME + "lib";
    private static final String THIRD_PARTY_DIRECTORY_NAME = FIT_ROOT_DIRECTORY_NAME + "third-party";

    private final Jar jar;
    private final PluginMetadata metadata;
    private final Plugin parent;
    private final PluginClassLoader pluginClassLoader;

    JarPlugin(Plugin parent, PluginMetadata metadata, Jar jar) {
        this.metadata = notNull(metadata, "The metadata of a JAR plugin cannot be null.");
        this.parent = notNull(parent, "The parent of a JAR plugin cannot be null.");
        this.jar = notNull(jar, "The JAR of a plugin cannot be null.");
        this.pluginClassLoader = new PluginClassLoader(parent.runtime().sharedClassLoader());
    }

    @Override
    public PluginMetadata metadata() {
        return this.metadata;
    }

    @Override
    public ClassLoader pluginClassLoader() {
        return this.pluginClassLoader;
    }

    @Override
    public FitRuntime runtime() {
        return this.parent.runtime();
    }

    @Override
    public Plugin parent() {
        return this.parent;
    }

    @Override
    protected void registerJars() {
        this.pluginClassLoader.add(NestedJarDiscovery.urlOfJar(this.jar.location()));
        NestedJarDiscovery discovery = new NestedJarDiscovery(this.jar);
        discovery.addConsumer(entry -> StringUtils.startsWithIgnoreCase(entry.name(), SHARED_DIRECTORY_NAME),
                this.runtime().registryOfSharedJars()::register);
        discovery.addConsumer(entry -> StringUtils.startsWithIgnoreCase(entry.name(), LIB_DIRECTORY_NAME),
                this.pluginClassLoader::add);
        discovery.addConsumer(entry -> StringUtils.startsWithIgnoreCase(entry.name(), THIRD_PARTY_DIRECTORY_NAME),
                this.pluginClassLoader::add);
        discovery.start();
    }
}
