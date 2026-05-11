// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.pipeline.huggingface.img2img;

import org.fitframework.fel.pipeline.huggingface.ExplicitPipeline;
import org.fitframework.fel.pipeline.huggingface.PipelineTask;
import org.fitframework.fel.service.pipeline.HuggingFacePipelineService;
import org.fitframework.resource.web.Media;

import java.util.List;

/**
 * 表示 {@link PipelineTask#IMAGE_TO_IMAGE} 任务的流水线。
 *
 * @author 易文渊
 * @since 2024-06-06
 */
public class Image2ImagePipeline extends ExplicitPipeline<Image2ImageInput, List<Media>> {
    /**
     * 创建图生图流水线的实例。
     *
     * @param model 表示模型名的 {@link String}。
     * @param service 表示提供 pipeline 服务的 {@link HuggingFacePipelineService}。
     */
    public Image2ImagePipeline(String model, HuggingFacePipelineService service) {
        super(PipelineTask.IMAGE_TO_IMAGE, model, service);
    }
}