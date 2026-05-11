// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.runtime.discrete;

import static org.fitframework.inspection.Validation.isTrue;
import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.maven.MavenCoordinate;
import org.fitframework.model.Version;
import org.fitframework.plugin.PluginCategory;
import org.fitframework.plugin.PluginKey;
import org.fitframework.plugin.PluginMetadata;
import org.fitframework.plugin.support.DefaultPluginKey;
import org.fitframework.plugin.support.DefaultPluginMetadata;
import org.fitframework.protocol.jar.Jar;
import org.fitframework.runtime.FitRuntime;
import org.fitframework.runtime.FitRuntimeStartupException;
import org.fitframework.runtime.support.AbstractRootPlugin;
import org.fitframework.util.ArrayUtils;
import org.fitframework.util.FileUtils;
import org.fitframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * 为根插件提供离散启动的实现。
 *
 * @author 季聿阶
 * @since 2023-07-29
 */
public class DiscreteRootPlugin extends AbstractRootPlugin {
    private final FitRuntime runtime;
    private final File frameworkDirectory;
    private final PluginMetadata metadata;

    DiscreteRootPlugin(FitRuntime runtime) {
        this.runtime = runtime;
        this.frameworkDirectory = notNull(FileUtils.file(runtime.location()),
                () -> new FitRuntimeStartupException("The framework directory cannot be null."));
        isTrue(this.frameworkDirectory.isDirectory(),
                () -> new FitRuntimeStartupException("The framework directory is not a directory."));
        this.metadata = buildPluginMetadata(this.frameworkDirectory, runtime.location());
    }

    private static PluginMetadata buildPluginMetadata(File frameworkDirectory, URL location) {
        // 框架目录下存在 fit-discrete-launcher-*.jar 的启动包
        File[] files = frameworkDirectory.listFiles((file, name) -> isLauncher(name));
        if (ArrayUtils.isEmpty(files)) {
            throw new FitRuntimeStartupException("Failed to locate launcher JAR.");
        }
        File launcher = files[0];

        Jar jar;
        try {
            jar = Jar.from(launcher);
        } catch (IOException e) {
            throw new FitRuntimeStartupException(StringUtils.format("Failed to load JAR of launcher. [location={0}]",
                    FileUtils.path(launcher)), e);
        }
        MavenCoordinate coordinate;
        try {
            coordinate = MavenCoordinate.read(jar);
        } catch (IOException e) {
            throw new FitRuntimeStartupException(StringUtils.format(
                    "Failed to read maven coordinate from JAR. [jar={0}]",
                    jar), e);
        }
        PluginKey pluginKey = new DefaultPluginKey(coordinate.groupId(),
                coordinate.artifactId(),
                Version.parse(coordinate.version()));
        return new DefaultPluginMetadata(pluginKey, location, PluginCategory.SYSTEM, Integer.MIN_VALUE);
    }

    private static boolean isLauncher(String name) {
        return StringUtils.startsWithIgnoreCase(name, "fit-discrete-launcher") && StringUtils.endsWithIgnoreCase(name,
                Jar.FILE_EXTENSION);
    }

    @Override
    public PluginMetadata metadata() {
        return this.metadata;
    }

    @Override
    public ClassLoader pluginClassLoader() {
        return DiscreteRootPlugin.class.getClassLoader();
    }

    @Override
    public FitRuntime runtime() {
        return this.runtime;
    }

    @Override
    protected void loadPlugins() {
        File pluginDirectory = new File(this.frameworkDirectory, "plugins");
        isTrue(pluginDirectory.isDirectory(),
                () -> new FitRuntimeStartupException("The plugin directory is not a directory."));
        File[] pluginFiles =
                pluginDirectory.listFiles((file, name) -> StringUtils.endsWithIgnoreCase(name, Jar.FILE_EXTENSION));
        if (pluginFiles == null) {
            return;
        }
        for (File pluginFile : pluginFiles) {
            this.loadPlugin(FileUtils.urlOf(pluginFile));
        }
    }
}
