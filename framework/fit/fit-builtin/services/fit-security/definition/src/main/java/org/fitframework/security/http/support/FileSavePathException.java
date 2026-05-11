// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.security.http.support;

import org.fitframework.security.http.FitSecurityException;

/**
 * 表示文件数量溢出异常。
 *
 * @author 何天放
 * @since 2024-07-29
 */
public class FileSavePathException extends FitSecurityException {
    /**
     * 使用异常信息初始化 {@link FileSavePathException} 类的新实例。
     *
     * @param message 表示异常信息的 {@link String}。
     */
    public FileSavePathException(String message) {
        super(message);
    }
}
