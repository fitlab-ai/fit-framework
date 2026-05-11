// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.security.http.upload;

import org.fitframework.http.entity.FileEntity;
import org.fitframework.security.http.FitSecurityException;

/**
 * 表示文件上传校验器。
 *
 * @author 何天放
 * @since 2024-07-11
 */
public interface FileUploadValidator {
    /**
     * 对文件进行校验。
     *
     * @param entity 表示文件的 {@link FileEntity}。
     * @param config 表示校验配置的 {@link FileUploadValidateConfig}。
     * @throws FitSecurityException 当文件上传校验未通过时。
     */
    void validate(FileEntity entity, FileUploadValidateConfig config) throws FitSecurityException;
}
