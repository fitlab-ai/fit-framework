// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.source.support;

import org.fitframework.fel.core.document.Document;
import org.fitframework.fel.core.source.AbstractFileSource;
import org.fitframework.exception.FitException;
import org.fitframework.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 表示文本文件加载器的实体。
 *
 * @author 易文渊
 * @since 2024-08-07
 */
public class TextFileSource extends AbstractFileSource {
    private final Charset charset;

    /**
     * 构造一个文本文件加载器，使用UTF-8作为字符集。
     */
    public TextFileSource() {
        this(StandardCharsets.UTF_8);
    }

    /**
     * 构造一个文本文件加载器，使用指定的字符集。
     *
     * @param charset 表示指定字符集的 {@link Charset}。
     */
    public TextFileSource(Charset charset) {
        this.charset = charset;
    }

    @Override
    protected List<Document> parse(File file) {
        try {
            String content = FileUtils.content(file, this.charset);
            return Collections.singletonList(Document.custom().text(content).metadata(new HashMap<>()).build());
        } catch (IOException e) {
            throw new FitException(e);
        }
    }
}