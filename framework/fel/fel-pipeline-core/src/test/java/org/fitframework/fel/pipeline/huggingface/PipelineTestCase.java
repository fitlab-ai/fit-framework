// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.pipeline.huggingface;

import lombok.Data;

/**
 * 表示 pipline 测试用例。
 *
 * @author 易文渊
 * @since 2024-06-07
 */
@Data
public class PipelineTestCase {
    private String task;
    private String model;
    private Object input;
    private Object output;
}