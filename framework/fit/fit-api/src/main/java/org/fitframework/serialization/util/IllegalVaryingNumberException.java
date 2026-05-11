// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.serialization.util;

/**
 * 当可变长度的数字无效时引发的异常。
 *
 * @author 梁济时
 * @since 2020-11-13
 */
public class IllegalVaryingNumberException extends RuntimeException {
    /**
     * 使用异常信息初始化 {@link IllegalVaryingNumberException} 类的新实例。
     *
     * @param message 表示异常信息的 {@link String}。
     */
    public IllegalVaryingNumberException(String message) {
        super(message);
    }
}
