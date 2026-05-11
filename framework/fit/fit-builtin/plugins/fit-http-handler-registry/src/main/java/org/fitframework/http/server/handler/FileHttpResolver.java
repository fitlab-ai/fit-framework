// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 表示文件的 http 请求解析器。
 *
 * @author 邬涨财
 * @since 2024-01-18
 */
public class FileHttpResolver extends AbstractFileHttpResolver<File> {
    private static final String TYPE = "file";

    @Override
    protected File getFile(String actualPath, ClassLoader classLoader) {
        return new File(actualPath);
    }

    @Override
    protected boolean isFileValid(File file) {
        return file.exists();
    }

    @Override
    protected InputStream getInputStream(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    @Override
    protected long getLength(File file, String actualPath, InputStream inputStream) {
        return file.length();
    }

    @Override
    protected String getFileName(File file) {
        return file.getName();
    }

    @Override
    protected String getType() {
        return TYPE;
    }
}
