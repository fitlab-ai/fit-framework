// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.document.support.postprocessor;

/**
 * RRF 算法 score 选择策略。
 *
 * @author 马朝阳
 * @since 2024-09-29
 */
public enum RrfScoreStrategy {
    /**
     * 相同文档的分数取最大值。
     */
    MAX,

    /**
     * 相同文档的分数取平均值。
     */
    AVG;
}
