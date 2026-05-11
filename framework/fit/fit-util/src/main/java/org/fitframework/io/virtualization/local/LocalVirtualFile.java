// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.io.virtualization.local;

import org.fitframework.inspection.Validation;
import org.fitframework.io.virtualization.VirtualDirectory;
import org.fitframework.io.virtualization.VirtualFile;
import org.fitframework.util.FileUtils;
import org.fitframework.util.ObjectUtils;
import org.fitframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 为 {@link VirtualFile} 提供基于本地文件系统的实现。
 *
 * @author 梁济时
 * @since 2022-06-06
 */
public class LocalVirtualFile implements VirtualFile {
    private final File file;

    /**
     * 使用所属的目录及当前文件初始化 {@link LocalVirtualFile} 类的新实例。
     *
     * @param file 表示文件数据的 {@link File}。
     */
    public LocalVirtualFile(File file) {
        Validation.notNull(file, "The data of virtual file cannot be null.");
        if (!file.exists()) {
            throw new IllegalArgumentException(StringUtils.format("The data of virtual file does not exist. [file={0}]",
                    FileUtils.path(file)));
        } else if (!file.isFile()) {
            throw new IllegalArgumentException(StringUtils.format("The data of virtual file is not a file. [file={0}]",
                    FileUtils.path(file)));
        } else {
            this.file = file;
        }
    }

    @Override
    public String name() {
        return this.file.getName();
    }

    @Override
    public String path() {
        return FileUtils.path(this.file);
    }

    @Override
    public VirtualDirectory directory() {
        File parent = this.file.getParentFile();
        return ObjectUtils.mapIfNotNull(parent, VirtualDirectory::of);
    }

    @Override
    public InputStream openRead() throws IOException {
        return new FileInputStream(this.file);
    }

    @Override
    public URL url() {
        try {
            return this.file.toURI().toURL();
        } catch (MalformedURLException ex) {
            throw new IllegalStateException(StringUtils.format("Failed to convert file to URL. [file={0}]",
                    FileUtils.path(this.file)), ex);
        }
    }
}
