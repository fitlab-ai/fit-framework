// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.source.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.fitframework.fel.core.document.Document;
import org.fitframework.fel.core.pattern.Source;
import org.fitframework.util.FileUtils;
import org.fitframework.util.MapBuilder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * 表示 {@link TextFileSource} 的测试集。
 *
 * @author 易文渊
 * @since 2024-08-09
 */
@DisplayName("测试 TextFileSource")
public class TextFileSourceTest {
    @Test
    @DisplayName("测试读取文本文件成功，结果符合预期")
    void giveExistFileThenReturnOk() {
        Source<File> fileSource = new TextFileSource();
        URL url = this.getClass().getClassLoader().getResource("test.txt");
        File file = FileUtils.file(url);
        assertThat(file).isNotNull();
        List<Document> documents = fileSource.load(file);
        assertThat(documents).hasSize(1)
                .first()
                .extracting(Document::text, Document::metadata)
                .containsExactly("This is a test.",
                        MapBuilder.<String, Object>get().put("source", file.getName()).build());
    }

    @Test
    @DisplayName("测试文件不存在，读取失败")
    void giveNotExistFileThenReturnError() {
        assertThatThrownBy(() -> {
            Source<File> fileSource = new TextFileSource();
            fileSource.load(null);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}