// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.document;

import org.fitframework.fel.core.embed.Embedding;

import java.util.List;

/**
 * 表示嵌入模型。
 *
 * @author 易文渊
 * @since 2024-08-12
 */
public interface DocumentEmbedModel {
    /**
     * 调用嵌入模型，批量生成嵌入向量。
     *
     * @param documents 表示输入文档列表的 {@link List}{@code <}{@link Document}{@code >}。
     * @return 表示生成嵌入向量的 {@link List}{@code <}{@link Embedding}{@code >}。
     */
    List<Embedding> embed(List<Document> documents);

    /**
     * 调用嵌入模型生成嵌入向量。
     *
     * @param input 表示用户输入的 {@link String}。
     * @return 表示模型生成嵌入响应的 {@link Embedding}。
     */
    Embedding embed(String input);
}