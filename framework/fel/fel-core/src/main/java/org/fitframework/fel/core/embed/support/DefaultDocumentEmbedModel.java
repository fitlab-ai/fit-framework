// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.embed.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.fel.core.document.Document;
import org.fitframework.fel.core.document.DocumentEmbedModel;
import org.fitframework.fel.core.embed.EmbedModel;
import org.fitframework.fel.core.embed.EmbedOption;
import org.fitframework.fel.core.embed.Embedding;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 表示 {@link DocumentEmbedModel} 的默认实现。
 *
 * @author 易文渊
 * @since 2024-08-12
 */
public class DefaultDocumentEmbedModel implements DocumentEmbedModel {
    private final EmbedModel service;
    private final EmbedOption embedOption;

    /**
     * 构造 {@link DefaultDocumentEmbedModel} 的实例。
     *
     * @param embedModel 表示用于嵌入生成服务的 {@link EmbedModel}。
     * @param embedOption 表示嵌入可选参数的 {@link EmbedOption}。
     * @throws IllegalArgumentException 当 {@code embedModel}、{@code embedOption} 为 {@code null} 时。
     */
    public DefaultDocumentEmbedModel(EmbedModel embedModel, EmbedOption embedOption) {
        this.service = notNull(embedModel, "The embed model cannot be null.");
        this.embedOption = notNull(embedOption, "The embed option cannot be null.");
    }

    @Override
    public List<Embedding> embed(List<Document> documents) {
        return documents.stream()
                .map(Document::text)
                .collect(Collectors.collectingAndThen(Collectors.toList(),
                        inputs -> this.service.generate(inputs, this.embedOption)));
    }

    @Override
    public Embedding embed(String input) {
        return this.service.generate(input, this.embedOption);
    }
}