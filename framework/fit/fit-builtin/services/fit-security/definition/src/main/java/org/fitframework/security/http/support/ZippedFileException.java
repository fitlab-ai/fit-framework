// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.security.http.support;

import org.fitframework.security.http.FitSecurityException;

/**
 * 表示压缩文件异常。
 *
 * @author 何天放
 * @since 2024-07-29
 */
public class ZippedFileException extends FitSecurityException {
    /**
     * 使用异常信息初始化 {@link ZippedFileException} 类的新实例。
     *
     * @param message 表示异常信息的 {@link String}。
     */
    public ZippedFileException(String message) {
        super(message);
    }
}
