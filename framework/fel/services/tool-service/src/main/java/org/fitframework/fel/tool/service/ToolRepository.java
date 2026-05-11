// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.tool.service;

import org.fitframework.fel.tool.ToolInfoEntity;
import org.fitframework.annotation.Genericable;

import java.util.List;

/**
 * 提供工具的存储服务。
 *
 * @author 鲁为
 * @author 杭潇
 * @since 2024-04-16
 */
public interface ToolRepository {
    /**
     * 添加工具。
     *
     * @param tool 表示待增加的工具信息的 {@link ToolInfoEntity}。
     */
    @Genericable(id = "org.fitframework.fel.tool.add")
    void addTool(ToolInfoEntity tool);

    /**
     * 删除工具。
     *
     * @param namespace 表示工具命名空间的 {@link String}。
     * @param toolName 表示待删除工具名称的 {@link String}。
     */
    @Genericable(id = "org.fitframework.fel.tool.delete")
    void deleteTool(String namespace, String toolName);

    /**
     * 获取工具。
     *
     * @param namespace 表示工具命名空间的 {@link String}。
     * @param toolName 表示工具名称的 {@link String}。
     * @return 表示工具的 {@link ToolInfoEntity}。
     */
    @Genericable(id = "org.fitframework.fel.tool.get")
    ToolInfoEntity getTool(String namespace, String toolName);

    /**
     * 获取命名空间下的所有工具。
     *
     * @param namespace 表示工具命名空间的 {@link String}。
     * @return 表示工具的 {@link List}{@code <}{@link ToolInfoEntity}{@code >}。
     */
    @Genericable(id = "org.fitframework.fel.tool.list")
    List<ToolInfoEntity> listTool(String namespace);
}
