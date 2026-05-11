// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.security.http.upload.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.entity.FileEntity;
import org.fitframework.security.http.FitSecurityException;
import org.fitframework.security.http.support.FileCountOverflowException;
import org.fitframework.security.http.upload.FileUploadValidateConfig;
import org.fitframework.security.http.upload.FileUploadValidator;
import org.fitframework.util.StringUtils;

import java.io.File;

/**
 * 表示 {@link FileUploadValidator} 的上传路径校验功能实现。
 *
 * @author 何天放
 * @since 2024-07-12
 */
public final class UploadPathValidator implements FileUploadValidator {
    /**
     * 表示 {@link FileUploadValidator} 的上传路径校验功能实现的实例。
     */
    public static final FileUploadValidator INSTANCE = new UploadPathValidator();

    private UploadPathValidator() {}

    @Override
    public void validate(FileEntity entity, FileUploadValidateConfig config) throws FitSecurityException {
        notNull(entity, "The file entity cannot be null.");
        notNull(config, "The config for file upload validate cannot be null.");
        String fileSavePath = config.fileSavePath();
        if (StringUtils.isBlank(fileSavePath)) {
            return;
        }
        File targetFile = new File(fileSavePath);
        String[] paths = targetFile.list();
        // 校验文件数量。
        if (config.fileSavePathFileCountLimit() > 0) {
            if (paths == null) {
                throw new FileCountOverflowException("Cannot get file count of file save path.");
            }
            if (paths.length >= config.fileSavePathFileCountLimit()) {
                throw new FileCountOverflowException(StringUtils.format("Too many files in target path. "
                                + "[fileCount={0}]",
                        paths.length));
            }
        }
        long freeSpace = targetFile.getFreeSpace();
        // 校验剩余空间。
        if (config.fileSavePathRestSpaceLimit() > 0
                && freeSpace - entity.length() < config.fileSavePathRestSpaceLimit()) {
            throw new FileCountOverflowException(StringUtils.format(
                    "No enough space in target path. [freeSpace={0}, fileSize={1}, "
                            + "fileSavePathRestSpaceLimit={2}]",
                    freeSpace,
                    entity.length(),
                    config.fileSavePathRestSpaceLimit()));
        }
    }
}
