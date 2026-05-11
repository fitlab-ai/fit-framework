// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.community.model.openai.enums;

/**
 * 模型内容生成状态枚举类。
 *
 * @author 孙怡菲
 * @since 2025-04-29
 */
public enum ModelProcessingState {
    /**
     * 表示初始状态。
     */
    INITIAL,

    /**
     * 表示内部推理状态。
     */
    THINKING,

    /**
     * 表示结果生成状态。
     */
    RESPONDING
}
