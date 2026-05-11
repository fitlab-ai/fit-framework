// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.tool.info.entity;

import java.util.List;

/**
 * 用于 tools.json 的序列化与反序列化的实体类。
 *
 * @author 曹嘉美
 * @since 2024-10-26
 */
public class ToolJsonEntity {
    private String version;
    private List<DefinitionGroupEntity> definitionGroups;
    private List<ToolGroupEntity> toolGroups;

    /**
     * 获取工具版本信息。
     *
     * @return 表示工具版本信息的 {@link String}。
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * 设置工具版本信息。
     *
     * @param version 表示工具版本信息的 {@link String}。
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * 获取定义组列表。
     *
     * @return 表示定义组列表的 {@link List}{@code <}{@link DefinitionGroupEntity}{@code >}。
     */
    public List<DefinitionGroupEntity> getDefinitionGroups() {
        return this.definitionGroups;
    }

    /**
     * 设置定义组列表。
     *
     * @param definitionGroups 表示定义组列表的 {@link List}{@code <}{@link DefinitionGroupEntity}{@code >}。
     */
    public void setDefinitionGroups(List<DefinitionGroupEntity> definitionGroups) {
        this.definitionGroups = definitionGroups;
    }

    /**
     * 获取工具组列表。
     *
     * @return 表示工具组列表的 {@link List}{@code <}{@link ToolGroupEntity}{@code >}。
     */
    public List<ToolGroupEntity> getToolGroups() {
        return this.toolGroups;
    }

    /**
     * 设置工具组列表。
     *
     * @param toolGroups 表示工具组列表的 {@link List}{@code <}{@link ToolGroupEntity}{@code >}。
     */
    public void setToolGroups(List<ToolGroupEntity> toolGroups) {
        this.toolGroups = toolGroups;
    }
}

