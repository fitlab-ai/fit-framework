// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.maven.complie.parser;

import org.fitframework.fel.tool.info.entity.ToolJsonEntity;

import java.io.IOException;

/**
 * 工具解析接口。
 *
 * @author 曹嘉美
 * @since 2024-10-26
 */
public interface GroupParser {
    /**
     * 基于目标目录将工具注解的方法解析成 {@link ToolJsonEntity}。
     *
     * @param outputDirectory 表示目标目录的 {@link String}。
     * @return 表示解析完之后的 {@link ToolJsonEntity}。
     * @throws IOException 当解析过程中发生输入输出异常时。
     */
    ToolJsonEntity parseJson(String outputDirectory) throws IOException;
}
