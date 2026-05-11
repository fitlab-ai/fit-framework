// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.pipeline.huggingface.img2img;

import lombok.Data;
import org.fitframework.fel.pipeline.PipelineInput;
import org.fitframework.annotation.Property;

/**
 * 表示图生图任务的输入参数。
 *
 * @author 易文渊
 * @since 2024-06-06
 */
@Data
public class Image2ImageInput implements PipelineInput {
    @Property(required = true)
    private String prompt;

    @Property(required = true)
    private String image;

    @Property(name = "negative_prompt")
    private String negativePrompt;

    @Property(name = "num_images_per_prompt")
    private Integer numImagesPerPrompt;

    @Property(name = "num_inference_steps")
    private Integer numInferenceSteps;
}