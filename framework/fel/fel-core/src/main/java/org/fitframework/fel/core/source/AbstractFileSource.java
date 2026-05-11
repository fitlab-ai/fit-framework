// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.source;

import org.fitframework.fel.core.document.Document;
import org.fitframework.fel.core.pattern.Source;
import org.fitframework.inspection.Validation;
import org.fitframework.util.MapBuilder;
import org.fitframework.util.MapUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 表示文件加载器的抽象实现。
 *
 * @author 易文渊
 * @since 2024-08-07
 */
public abstract class AbstractFileSource implements Source<File> {
    @Override
    public List<Document> load(File input) {
        List<Document> documents = this.parse(input);
        Map<String, Object> custom = this.getCustomMetadata(input);
        if (MapUtils.isNotEmpty(custom)) {
            documents.forEach(d -> Validation.notNull(d.metadata(), "The metadata cannot be null.").putAll(custom));
        }
        return documents;
    }

    /**
     * 解析输入文件并生成文档列表。
     *
     * @param file 表示输入文件的 {@link File}。
     * @return 表示生成的文档列表的 {@link List}{@code <}{@link Document}。
     * @throws org.fitframework.exception.FitException 当文件解析失败时。
     */
    protected abstract List<Document> parse(File file);

    /**
     * 获取自定义的元数据。
     *
     * @param file 表示输入文件的 {@link File}。
     * @return 表示自定义元数据的 {@link Map}{@code <}{@link String}{@code , }{@link Object}。
     */
    protected Map<String, Object> getCustomMetadata(File file) {
        return MapBuilder.<String, Object>get().put("source", file.getName()).build();
    }
}