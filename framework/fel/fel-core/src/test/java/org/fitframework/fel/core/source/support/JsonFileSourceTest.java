// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.source.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.fitframework.fel.core.document.Document;
import org.fitframework.fel.core.pattern.Source;
import org.fitframework.fel.core.template.StringTemplate;
import org.fitframework.serialization.json.jackson.JacksonObjectSerializer;
import org.fitframework.serialization.ObjectSerializer;
import org.fitframework.util.FileUtils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * 表示 {@link JsonFileSource} 的测试类。
 *
 * @author 易文渊
 * @since 2024-08-09
 */
@DisplayName("测试 JsonFileSource")
public class JsonFileSourceTest {
    @Test
    @DisplayName("测试读取 json 文件成功，结果符合预期")
    void giveExistFileThenReturnOk() {
        ObjectSerializer serializer = new JacksonObjectSerializer(null, null, null, true);
        Source<File> fileSource = new JsonFileSource(serializer, StringTemplate.create("{{instruction}}\n{{output}}"));
        URL url = this.getClass().getClassLoader().getResource("test.json");
        File file = FileUtils.file(url);
        List<Document> documents = fileSource.load(file);
        assertThat(documents).hasSize(2)
                .flatMap(Document::text)
                .containsExactly("instruction1\noutput1", "instruction2\noutput2");
    }
}