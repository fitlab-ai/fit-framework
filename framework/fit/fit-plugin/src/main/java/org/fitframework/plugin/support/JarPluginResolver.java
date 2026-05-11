// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.plugin.support;

import org.fitframework.inspection.Validation;
import org.fitframework.maven.MavenCoordinate;
import org.fitframework.model.Version;
import org.fitframework.plugin.Plugin;
import org.fitframework.plugin.PluginCategory;
import org.fitframework.plugin.PluginKey;
import org.fitframework.plugin.PluginMetadata;
import org.fitframework.plugin.PluginResolver;
import org.fitframework.protocol.jar.Jar;
import org.fitframework.util.CodeableEnum;
import org.fitframework.util.ObjectUtils;
import org.fitframework.util.StringUtils;
import org.fitframework.util.XmlUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 为 {@link PluginResolver} 提供默认实现。
 *
 * @author 梁济时
 * @since 2022-06-06
 */
public final class JarPluginResolver implements PluginResolver {
    /**
     * 获取 {@link JarPluginResolver} 提供唯一实例。
     */
    public static final JarPluginResolver INSTANCE = new JarPluginResolver();

    private static final String MANIFEST_ENTRY_NAME = "FIT-INF/plugin.xml";

    private JarPluginResolver() {}

    @Override
    public Plugin resolve(Plugin parent, URL location) {
        Validation.notNull(location, "The location of plugin to resolve cannot be null.");
        Jar jar;
        try {
            jar = Jar.from(location);
        } catch (IOException e) {
            throw new IllegalStateException(StringUtils.format("Failed to resolve JAR of plugin. [location={0}]",
                    location), e);
        }
        PluginMetadata metadata = loadMetadata(jar);
        return new JarPlugin(parent, metadata, jar);
    }

    private static PluginMetadata loadMetadata(Jar jar) {
        MavenCoordinate coordinate = loadMavenCoordinate(jar);
        Document xml = readPluginManifest(jar);
        Element root = XmlUtils.child(xml, "plugin");
        String group = getGroup(root, coordinate);
        String name = getName(root, coordinate);
        String hierarchicalNames = XmlUtils.content(root, "hierarchicalNames");
        String version = getVersion(root, coordinate);
        PluginCategory category = CodeableEnum.fromCode(PluginCategory.class, XmlUtils.content(root, "category"));
        int level = Integer.parseInt(XmlUtils.content(root, "level"));
        URL pluginLocation = getLocation(jar);
        PluginKey pluginKey = new DefaultPluginKey(group, name, Version.parse(version));
        return new DefaultPluginMetadata(pluginKey,
                hierarchicalNames,
                pluginLocation,
                ObjectUtils.nullIf(category, PluginCategory.USER),
                ObjectUtils.nullIf(level, 4));
    }

    private static URL getLocation(Jar jar) {
        try {
            return jar.location().toUrl();
        } catch (MalformedURLException e) {
            throw new IllegalStateException(StringUtils.format("Failed to obtain URL of JAR. [jar={0}]", jar), e);
        }
    }

    private static String getGroup(Element root, MavenCoordinate coordinate) {
        String group = XmlUtils.content(root, "group");
        if (StringUtils.isNotBlank(group)) {
            return group;
        }
        return coordinate.groupId();
    }

    private static String getName(Element root, MavenCoordinate coordinate) {
        String name = XmlUtils.content(root, "name");
        if (StringUtils.isNotBlank(name)) {
            return name;
        }
        return coordinate.artifactId();
    }

    private static String getVersion(Element root, MavenCoordinate coordinate) {
        String version = XmlUtils.content(root, "version");
        if (StringUtils.isNotBlank(version)) {
            return version;
        }
        return coordinate.version();
    }

    private static Document readPluginManifest(Jar jar) {
        Jar.Entry entry = jar.entries().get(MANIFEST_ENTRY_NAME);
        if (entry == null) {
            throw new IllegalStateException(StringUtils.format(
                    "Missing manifest in plugin JAR. [location={0}, entry={1}]",
                    jar.location(),
                    MANIFEST_ENTRY_NAME));
        }
        try (InputStream in = entry.read()) {
            return XmlUtils.load(in);
        } catch (IOException e) {
            throw new IllegalStateException(StringUtils.format("Failed to load plugin metadata in JAR. [url={0}]",
                    entry), e);
        }
    }

    private static MavenCoordinate loadMavenCoordinate(Jar jar) {
        try {
            return MavenCoordinate.read(jar);
        } catch (IOException e) {
            throw new IllegalStateException(StringUtils.format("Failed to load maven metadata in JAR. [jar={0}]", jar),
                    e);
        }
    }
}
