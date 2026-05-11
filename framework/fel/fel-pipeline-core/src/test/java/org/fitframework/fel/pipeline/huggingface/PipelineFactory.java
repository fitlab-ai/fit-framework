// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.pipeline.huggingface;

import org.fitframework.fel.pipeline.Pipeline;
import org.fitframework.fel.pipeline.huggingface.asr.AsrPipeline;
import org.fitframework.fel.pipeline.huggingface.img2img.Image2ImagePipeline;
import org.fitframework.fel.pipeline.huggingface.text2img.Text2ImagePipeline;
import org.fitframework.fel.pipeline.huggingface.tts.TtsPipeline;
import org.fitframework.fel.service.pipeline.HuggingFacePipelineService;
import org.fitframework.inspection.Validation;
import org.fitframework.util.MapBuilder;
import org.fitframework.util.ObjectUtils;
import org.fitframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * 表示 pipeline 工厂。
 *
 * @author 易文渊
 * @since 2024-06-07
 */
public class PipelineFactory {
    private static final Map<String, Class<?>> PIPELINE_CLAZZ = MapBuilder.<String, Class<?>>get()
            .put(PipelineTask.AUTOMATIC_SPEECH_RECOGNITION.getId(), AsrPipeline.class)
            .put(PipelineTask.TEXT_TO_SPEECH.getId(), TtsPipeline.class)
            .put(PipelineTask.IMAGE_TO_IMAGE.getId(), Image2ImagePipeline.class)
            .put(PipelineTask.TEXT_TO_IMAGE.getId(), Text2ImagePipeline.class)
            .build();

    /**
     * 创建 pipeline 实例。
     *
     * @param task 表示任务类型的 {@link PipelineTask}。
     * @param model 表示模型名的 {@link String}。
     * @param service 表示提供 pipeline 服务的 {@link HuggingFacePipelineService}。
     * @return 表示创建流水线实例的 {@link Pipeline}。
     */
    public static Pipeline create(String task, String model, HuggingFacePipelineService service) {
        Class<?> clazz = PIPELINE_CLAZZ.get(task);
        Validation.notNull(clazz, "The task '{0}' class cannot be null.", task);
        Constructor<?> constructor =
                ReflectionUtils.getDeclaredConstructor(clazz, String.class, HuggingFacePipelineService.class);
        return ObjectUtils.cast(ReflectionUtils.instantiate(constructor, model, service));
    }
}