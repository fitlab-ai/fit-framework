// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.security.http.zipped;

import org.fitframework.security.http.name.FileNameValidateConfig;

/**
 * 表示可配置的压缩文件校验配置。
 *
 * @author 何天放
 * @since 2024-07-18
 */
public interface ConfigurableZippedFileValidateConfig extends ZippedFileValidateConfig {
    /**
     * 设置压缩文件中各文件名校验配置。
     *
     * @param fileNameValidateConfig 表示文件名校验配置的 {@link FileNameValidateConfig}。
     * @return 表示当前可配置的文件名校验配置的 {@link ConfigurableZippedFileValidateConfig}。
     */
    ConfigurableZippedFileValidateConfig fileNameValidateConfig(FileNameValidateConfig fileNameValidateConfig);

    /**
     * 设置压缩文件最多允许的子目录数量。
     *
     * @param zippedFileEntryCountLimit 表示最多允许的子目录数量的 {@code long}。
     * @return 表示当前可配置的文件名校验配置的 {@link ConfigurableZippedFileValidateConfig}。
     */
    ConfigurableZippedFileValidateConfig zippedFileEntryCountLimit(long zippedFileEntryCountLimit);

    /**
     * 设置压缩文件总大小限制。
     *
     * @param zippedFileTotalSizeLimit 表示总大小限制的 {@code long}。
     * @return 表示当前可配置的文件名校验配置的 {@link ConfigurableZippedFileValidateConfig}。
     */
    ConfigurableZippedFileValidateConfig zippedFileTotalSizeLimit(long zippedFileTotalSizeLimit);
}
