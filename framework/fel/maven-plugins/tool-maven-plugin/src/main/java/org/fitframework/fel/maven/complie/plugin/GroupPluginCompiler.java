// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.maven.complie.plugin;

import org.fitframework.fel.tool.info.entity.ToolJsonEntity;
import org.fitframework.plugin.maven.support.AbstractCompiler;
import org.fitframework.fel.maven.complie.parser.ByteBuddyGroupParser;
import org.fitframework.fel.maven.complie.parser.GroupParser;

import net.bytebuddy.pool.TypePool;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import java.io.IOException;
import java.net.URLClassLoader;

/**
 * 向目标文件夹下输出编译产物。
 *
 * @author 曹嘉美
 * @since 2024-10-26
 */
public class GroupPluginCompiler extends AbstractCompiler {
    GroupPluginCompiler(MavenProject project, Log log) {
        super(project, log, null);
    }

    @Override
    protected void output(String outputDirectory, String fitRootDirectory) throws MojoExecutionException {
        UrlClassLoaderInitializer urlClassLoaderInitializer = new UrlClassLoaderInitializer();
        try (URLClassLoader classLoader = urlClassLoaderInitializer.initUrlClassLoader(outputDirectory,
                FIT_ROOT_DIRECTORY)) {
            GroupParser groupParser = new ByteBuddyGroupParser(TypePool.Default.of(classLoader), outputDirectory);
            ToolJsonEntity toolJsonEntity = groupParser.parseJson(outputDirectory);
            urlClassLoaderInitializer.outputToolManifest(outputDirectory, toolJsonEntity);
        } catch (IOException e) {
            throw new MojoExecutionException("Failed to parse class files.", e);
        }
    }
}
