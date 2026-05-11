// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.security.http.upload;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.fitframework.http.entity.FileEntity;
import org.fitframework.security.http.FitSecurityException;
import org.fitframework.security.http.support.FileSavePathException;
import org.fitframework.security.http.upload.support.SavePathUploadValidator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * 表示 {@link SavePathUploadValidator} 的测试。
 *
 * @author 何天放
 * @since 2024-07-16
 */
@DisplayName("测试文件保存路径校验器")
public final class SavePathValidatorTest {
    @Test
    @DisplayName("当文件路径为空时须返回校验通过")
    void shouldReturnOkWhenFilePathIsEmpty() {
        FileEntity entity = mock(FileEntity.class);
        FileUploadValidateConfig config = Mockito.mock(FileUploadValidateConfig.class);
        when(config.fileSavePath()).thenReturn("");
        boolean failed = false;
        try {
            SavePathUploadValidator.INSTANCE.validate(entity, config);
        } catch (FitSecurityException e) {
            failed = true;
        }
        assertThat(failed).isFalse();
    }

    @Test
    @DisplayName("当文件名非法带来跨路径风险时须返回文件保存路径异常")
    void shouldReturnFileSavePathErrorWhenFileNameIsIllegal() {
        FileEntity entity = mock(FileEntity.class);
        when(entity.filename()).thenReturn("../file_name_for_test.txt");
        FileUploadValidateConfig config = Mockito.mock(FileUploadValidateConfig.class);
        when(config.fileSavePath()).thenReturn(".");
        boolean failed = false;
        try {
            SavePathUploadValidator.INSTANCE.validate(entity, config);
        } catch (FitSecurityException e) {
            failed = true;
            assertThat(e).isInstanceOf(FileSavePathException.class);
        }
        assertThat(failed).isTrue();
    }
}
