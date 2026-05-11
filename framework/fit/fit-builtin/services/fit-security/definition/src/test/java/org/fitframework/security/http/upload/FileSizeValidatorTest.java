// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.security.http.upload;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.fitframework.http.entity.FileEntity;
import org.fitframework.security.http.FitSecurityException;
import org.fitframework.security.http.support.FileSizeException;
import org.fitframework.security.http.upload.support.FileSizeUploadValidator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * 表示 {@link FileSizeUploadValidator} 的测试。
 *
 * @author 何天放
 * @since 2024-07-16
 */
@DisplayName("测试文件大小校验器")
public final class FileSizeValidatorTest {
    @Test
    @DisplayName("当文件大小阈值为负时须返回校验通过")
    void shouldReturnOkWhenLimitIsNegative() {
        FileEntity entity = createFileEntity(1000L);
        FileUploadValidateConfig config = createFileValidateConfig(-1L);
        boolean failed = false;
        try {
            FileSizeUploadValidator.INSTANCE.validate(entity, config);
        } catch (FitSecurityException e) {
            failed = true;
        }
        assertThat(failed).isFalse();
    }

    @Test
    @DisplayName("当文件大小小于等于阈值时须返回校验通过")
    void shouldReturnOkWhenEntitySizeIsSmallerThanLimit() {
        FileEntity entity = createFileEntity(1000L);
        FileUploadValidateConfig config = createFileValidateConfig(1000L);
        boolean failed = false;
        try {
            FileSizeUploadValidator.INSTANCE.validate(entity, config);
        } catch (FitSecurityException e) {
            failed = true;
        }
        assertThat(failed).isFalse();
    }

    @Test
    @DisplayName("当文件大小大于阈值时须返回文件大小错误")
    void shouldReturnFileSizeErrorWhenEntitySizeIsBiggerThanLimit() {
        FileEntity entity = createFileEntity(1000L);
        FileUploadValidateConfig config = createFileValidateConfig(100L);
        boolean failed = false;
        try {
            FileSizeUploadValidator.INSTANCE.validate(entity, config);
        } catch (FitSecurityException e) {
            assertThat(e).isInstanceOf(FileSizeException.class);
            failed = true;
        }
        assertThat(failed).isTrue();
    }

    @Test
    @DisplayName("当空间不足时须返回文件大小错误")
    void shouldReturnFileSizeErrorWhenNotEnoughFreeMemory() {
        FileEntity entity = createFileEntity(Long.MAX_VALUE - 1);
        FileUploadValidateConfig config = createFileValidateConfig(Long.MAX_VALUE);
        boolean failed = false;
        try {
            FileSizeUploadValidator.INSTANCE.validate(entity, config);
        } catch (FitSecurityException e) {
            assertThat(e).isInstanceOf(FileSizeException.class);
            failed = true;
        }
        assertThat(failed).isTrue();
    }

    private static FileEntity createFileEntity(long fileSize) {
        FileEntity entity = mock(FileEntity.class);
        when(entity.length()).thenReturn(fileSize);
        return entity;
    }

    private static FileUploadValidateConfig createFileValidateConfig(long fileSizeLimit) {
        FileUploadValidateConfig config = Mockito.mock(FileUploadValidateConfig.class);
        when(config.fileSizeLimit()).thenReturn(fileSizeLimit);
        return config;
    }
}
