// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.format;

/**
 * 表示格式化器的接口，提供包含语言模型的输出应该如何格式化的提示词。
 *
 * @author 易文渊
 * @since 2024-04-28
 */
@FunctionalInterface
public interface FormatProvider {
    /**
     * 获取规范模型输出的提示词。
     *
     * @return 表示提示词的 {@link String}。
     */
    String instruction();
}