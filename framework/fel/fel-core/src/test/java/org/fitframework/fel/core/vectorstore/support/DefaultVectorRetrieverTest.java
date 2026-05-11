// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.vectorstore.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.fitframework.fel.core.document.Document;
import org.fitframework.fel.core.document.MeasurableDocument;
import org.fitframework.fel.core.embed.EmbedOption;
import org.fitframework.fel.core.embed.support.DefaultDocumentEmbedModel;
import org.fitframework.fel.core.pattern.Retriever;
import org.fitframework.fel.core.vectorstore.SearchOption;
import org.fitframework.fel.core.vectorstore.VectorStore;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 表示 {@link DefaultVectorRetriever} 的单元测试。
 *
 * @author 易文渊
 * @since 2024-08-08
 */
@DisplayName("测试 DefaultVectorRetriever")
public class DefaultVectorRetrieverTest {
    @Test
    @DisplayName("测试插入数据后，向量检索正常")
    void shouldOkWhenRetrieve() {
        List<Document> documents = EmbedModelStub.generateTestDocuments();
        VectorStore vectorStore = new MemoryVectorStore(new DefaultDocumentEmbedModel(new EmbedModelStub(),
                EmbedOption.custom().build()));
        vectorStore.persistent(documents);
        Retriever<String, MeasurableDocument> retriever =
                new DefaultVectorRetriever(vectorStore, SearchOption.custom().topK(1).build());
        assertThat(retriever.retrieve("test0")).hasSize(1)
                .first()
                .extracting(Document::text)
                .isEqualTo(documents.get(0).text());
    }
}