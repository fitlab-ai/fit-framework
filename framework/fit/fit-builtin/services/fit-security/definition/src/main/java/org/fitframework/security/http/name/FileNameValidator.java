// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.security.http.name;

import org.fitframework.security.http.FitSecurityException;

/**
 * 表示文件名校验器。
 *
 * @author 何天放
 * @since 2024-07-18
 */
public interface FileNameValidator {
    /**
     * 对文件名进行校验。
     *
     * @param processedFileName 表示经过预处理后的文件名的 {@link String}。
     * @param config 表示文件名校验配置的 {@link FileNameValidateConfig}。
     * @throws FitSecurityException 当文件名校验不通过时。
     */
    void validate(String processedFileName, FileNameValidateConfig config) throws FitSecurityException;
}
