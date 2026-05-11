// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.community.model.openai.entity.embed;

import org.fitframework.fel.core.embed.Embedding;

import java.util.List;

/**
 * 表示 OpenAi 格式的嵌入向量。
 *
 * @author 易文渊
 * @author 张庭怿
 * @since 2024-4-30
 */
public class OpenAiEmbedding implements Embedding {
    private List<Float> embedding;

    @Override
    public List<Float> embedding() {
        return this.embedding;
    }
}
