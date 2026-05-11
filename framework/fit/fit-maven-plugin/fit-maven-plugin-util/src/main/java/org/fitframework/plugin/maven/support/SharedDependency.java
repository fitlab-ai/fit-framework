// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.plugin.maven.support;

/**
 * 表示共享的依赖信息。
 *
 * @author 季聿阶
 * @since 2023-08-21
 */
public class SharedDependency {
    private String groupId;
    private String artifactId;

    /**
     * 获取依赖的 {@code 'groupId'}。
     *
     * @return 表示依赖的 {@code 'groupId'} 的 {@link String}。
     */
    public String getGroupId() {
        return this.groupId;
    }

    /**
     * 设置依赖的 {@code 'groupId'}。
     *
     * @param groupId 表示依赖的 {@code 'groupId'} 的 {@link String}。
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * 获取依赖的 {@code 'artifactId'}。
     *
     * @return 表示依赖的 {@code 'artifactId'} 的 {@link String}。
     */
    public String getArtifactId() {
        return this.artifactId;
    }

    /**
     * 设置依赖的 {@code 'artifactId'}。
     *
     * @param artifactId 表示依赖的 {@code 'artifactId'} 的 {@link String}。
     */
    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }
}
