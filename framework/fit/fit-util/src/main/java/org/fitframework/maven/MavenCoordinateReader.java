// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.maven;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.protocol.jar.Jar;
import org.fitframework.protocol.jar.JarEntryLocation;
import org.fitframework.util.StringUtils;
import org.fitframework.util.XmlUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * 为 {@link MavenCoordinate} 提供读取程序。
 *
 * @author 梁济时
 * @since 2022-10-21
 */
final class MavenCoordinateReader {
    private static final String ENTRY_PREFIX = "META-INF/maven/";
    private static final String MAVEN_GROUP_ID_KEY = "groupId";
    private static final String MAVEN_ARTIFACT_ID_KEY = "artifactId";
    private static final String MAVEN_VERSION_KEY = "version";
    private static final String MAVEN_XML_ROOT_NODE_NAME = "project";
    private static final String PARENT_NODE_NAME = "parent";

    /**
     * 隐藏默认构造方法，避免工具类被实例化。
     */
    private MavenCoordinateReader() {}

    /**
     * 从指定 JAR 中读取 Maven 坐标信息。
     *
     * @param jar 表示待读取 Maven 坐标的 JAR 的 {@link Jar}。
     * @return 表示读取到的 Maven 坐标的 {@link MavenCoordinate}。
     * @throws IOException 读取过程发生输入输出异常。
     */
    static MavenCoordinate read(Jar jar) throws IOException {
        notNull(jar, "The JAR to read maven coordinate cannot be null.");
        Optional<Jar.Entry> pom = jar.entries().stream().filter(entry -> isPom(entry.name())).findAny();
        if (pom.isPresent()) {
            try (InputStream in = pom.get().read()) {
                return read(in);
            }
        } else {
            return null;
        }
    }

    private static boolean isPom(String name) {
        return StringUtils.startsWithIgnoreCase(name, ENTRY_PREFIX) && StringUtils.endsWithIgnoreCase(name,
                MavenCoordinate.POM_FILE_NAME)
                && name.charAt(name.length() - MavenCoordinate.POM_FILE_NAME.length() - 1)
                == JarEntryLocation.ENTRY_PATH_SEPARATOR;
    }

    static MavenCoordinate read(InputStream in) {
        Document document = XmlUtils.load(in);
        Node project = XmlUtils.holder(MAVEN_XML_ROOT_NODE_NAME).accept(document.getChildNodes()).require();
        XmlUtils.NodeHolder parent = XmlUtils.holder(PARENT_NODE_NAME);
        XmlUtils.NodeHolder groupId = XmlUtils.holder(MAVEN_GROUP_ID_KEY);
        XmlUtils.NodeHolder artifactId = XmlUtils.holder(MAVEN_ARTIFACT_ID_KEY);
        XmlUtils.NodeHolder version = XmlUtils.holder(MAVEN_VERSION_KEY);
        XmlUtils.NodeHolder.accept(project.getChildNodes(), parent, groupId, artifactId, version);
        if (parent.ready()) {
            XmlUtils.NodeHolder.accept(parent.children(), groupId, version);
        }
        return MavenCoordinate.create(groupId.stringValue(), artifactId.stringValue(), version.stringValue());
    }
}
