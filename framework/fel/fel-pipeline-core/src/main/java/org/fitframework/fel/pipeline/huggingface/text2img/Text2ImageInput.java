// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.pipeline.huggingface.text2img;

import lombok.Data;
import org.fitframework.fel.pipeline.PipelineInput;
import org.fitframework.annotation.Property;

/**
 * 表示文生图任务的输入参数。
 *
 * @author 易文渊
 * @since 2024-06-06
 */
@Data
public class Text2ImageInput implements PipelineInput {
    private String prompt;

    @Property(name = "negative_prompt")
    private String negativePrompt;

    private Integer height;

    private Integer width;

    @Property(name = "num_images_per_prompt")
    private Integer numImagesPerPrompt;

    @Property(name = "num_inference_steps")
    private Integer numInferenceSteps;
}