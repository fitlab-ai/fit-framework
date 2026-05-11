// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.parameterization;

/**
 * 当发现参数化字符串的格式错误，或参数未提供时引发的异常。
 *
 * @author 梁济时
 * @since 2020-07-24
 */
public class StringFormatException extends IllegalArgumentException {
    /**
     * 使用异常信息初始化 {@link StringFormatException} 类的新实例。
     *
     * @param message 表示异常信息的 {@link String}。
     */
    public StringFormatException(String message) {
        super(message);
    }
}
