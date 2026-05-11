// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.pipeline.huggingface.tts;

import lombok.Data;
import org.fitframework.fel.pipeline.PipelineInput;
import org.fitframework.annotation.Property;

import java.util.Map;

/**
 * 表示语音合成任务的输入参数。
 *
 * @author 易文渊
 * @since 2024-06-05
 */
@Data
public class TtsInput implements PipelineInput {
    /**
     * 表示输入文本的 {@link String}。
     */
    @Property(name = "text_inputs")
    private String textInputs;

    /**
     * 表示底层模型推理参数的 {@link Map}{@code <}{@link String}{@code , }{@link Object}{@code >}。
     */
    @Property(name = "forward_params")
    private Map<String, Object> forwardParams;

    /**
     * 表示音频模型推理参数的 {@link Map}{@code <}{@link String}{@code , }{@link Object}{@code >}。
     */
    @Property(name = "generate_kwargs")
    private Map<String, Object> generateKwargs;
}