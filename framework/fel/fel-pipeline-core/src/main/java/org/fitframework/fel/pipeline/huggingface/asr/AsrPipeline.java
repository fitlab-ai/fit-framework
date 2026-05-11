// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.pipeline.huggingface.asr;

import org.fitframework.fel.pipeline.huggingface.ExplicitPipeline;
import org.fitframework.fel.pipeline.huggingface.PipelineTask;
import org.fitframework.fel.service.pipeline.HuggingFacePipelineService;

/**
 * 表示 {@link PipelineTask#AUTOMATIC_SPEECH_RECOGNITION} 任务的流水线。
 *
 * @author 易文渊
 * @since 2024-06-04
 */
public class AsrPipeline extends ExplicitPipeline<AsrInput, AsrOutput> {
    /**
     * 创建语音识别流水线的实例。
     *
     * @param model 表示模型名的 {@link String}。
     * @param service 表示提供 pipeline 服务的 {@link HuggingFacePipelineService}。
     */
    public AsrPipeline(String model, HuggingFacePipelineService service) {
        super(PipelineTask.AUTOMATIC_SPEECH_RECOGNITION, model, service);
    }
}