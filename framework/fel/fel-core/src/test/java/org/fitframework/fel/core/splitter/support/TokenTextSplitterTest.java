// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.splitter.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.fitframework.fel.core.document.Document;
import org.fitframework.fel.core.pattern.Splitter;
import org.fitframework.fel.core.tokenizer.Tokenizer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

/**
 * 表示 {@link TokenTextSplitter} 的测试集。
 *
 * @author 易文渊
 * @since 2024-08-09
 */
@DisplayName("测试 TokenTextSplitter")
public class TokenTextSplitterTest {
    @Test
    @DisplayName("按照 token 数切分正常文档，返回正确结果")
    void giveDocumentThenReturnOk() {
        Tokenizer tokenizer = new SimpleTokenizer();
        Splitter<Document> splitter = new TokenTextSplitter(tokenizer, 2, 1);
        Document document = Document.custom().text("This is test.").metadata(new HashMap<>()).build();
        assertThat(splitter.split(document)).hasSize(12);
    }

    @Test
    @DisplayName("按照 token 数切分空文档，返回空列表")
    void giveEmptyDocumentThenReturnOk() {
        Tokenizer tokenizer = new SimpleTokenizer();
        Splitter<Document> splitter = new TokenTextSplitter(tokenizer);
        Document document = Document.custom().text("").metadata(new HashMap<>()).build();
        assertThat(splitter.split(document)).hasSize(0);
    }
}