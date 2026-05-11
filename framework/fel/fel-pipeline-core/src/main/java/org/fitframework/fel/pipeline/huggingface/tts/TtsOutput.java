// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.pipeline.huggingface.tts;

import lombok.Data;
import org.fitframework.annotation.Property;
import org.fitframework.resource.web.Media;

/**
 * 表示语音合成任务的输出参数。
 *
 * @author 易文渊
 * @since 2024-06-05
 */
@Data
public class TtsOutput {
    /**
     * 表示输出音频的 {@link Media}。
     */
    private Media audio;

    @Property(name = "sampling_rate")
    private Integer samplingRate;
}