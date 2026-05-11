// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.pipeline.huggingface.tts;

import org.fitframework.fel.pipeline.huggingface.ExplicitPipeline;
import org.fitframework.fel.pipeline.huggingface.PipelineTask;
import org.fitframework.fel.service.pipeline.HuggingFacePipelineService;

/**
 * 表示 {@link PipelineTask#TEXT_TO_SPEECH} 任务的流水线。
 *
 * @author 易文渊
 * @since 2024-06-05
 */
public class TtsPipeline extends ExplicitPipeline<TtsInput, TtsOutput> {
    /**
     * 创建语音合成流水线的实例。
     *
     * @param model 表示模型名的 {@link String}。
     * @param service 表示提供 pipeline 服务的 {@link HuggingFacePipelineService}。
     */
    public TtsPipeline(String model, HuggingFacePipelineService service) {
        super(PipelineTask.TEXT_TO_SPEECH, model, service);
    }
}