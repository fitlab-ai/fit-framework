// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.splitter;

import org.fitframework.fel.core.document.Document;
import org.fitframework.fel.core.pattern.Splitter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 表示文本分割器的抽象实现。
 *
 * @author 易文渊
 * @since 2024-08-06
 */
public abstract class AbstractTextSplitter implements Splitter<Document> {
    @Override
    public List<Document> split(Document document) {
        return this.splitText(document.text())
                .stream()
                .map(chunk -> Document.custom().text(chunk).metadata(document.metadata()).build())
                .collect(Collectors.toList());
    }

    /**
     * 分割给定的文本。
     *
     * @param text 表示需要被分割文本的 {@link String}。
     * @return 表示分割后文本列表的 {@link String}{@code <}{@link String}{@code >}。
     */
    protected abstract List<String> splitText(String text);
}