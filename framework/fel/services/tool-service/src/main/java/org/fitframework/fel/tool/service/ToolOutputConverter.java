// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.tool.service;

import org.fitframework.annotation.Genericable;

/**
 * 表示工具输出转换器。
 *
 * @author 易文渊
 * @since 2024-08-14
 */
public interface ToolOutputConverter {
    /**
     * 将工具输出进行转换。
     *
     * @param object 表示待序列化对象的 {@link Object}。
     * @return 表示序列化结果的 {@link String}。
     */
    @Genericable(id = "org.fitframework.fel.tool.convert")
    String convert(Object object);
}