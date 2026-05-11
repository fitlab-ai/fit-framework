// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.client;

import org.fitframework.exception.ErrorCode;
import org.fitframework.exception.FitException;

/**
 * 表示当无法找到指定的服务时引发的异常。
 *
 * @author 梁济时
 * @author 季聿阶
 * @since 2020-09-03
 */
@ErrorCode(GenericableNotFoundException.CODE)
public class GenericableNotFoundException extends FitException {
    /** 表示没有服务的异常码。 */
    public static final int CODE = 0x7F010001;

    /**
     * 通过异常信息来实例化 {@link GenericableNotFoundException}。
     *
     * @param message 表示异常信息的 {@link String}。
     */
    public GenericableNotFoundException(String message) {
        super(message);
    }

    /**
     * 通过异常原因来实例化 {@link GenericableNotFoundException}。
     *
     * @param cause 表示异常原因的 {@link Throwable}。
     */
    public GenericableNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * 通过异常信息和异常原因来实例化 {@link GenericableNotFoundException}。
     *
     * @param message 表示异常信息的 {@link String}。
     * @param cause 表示异常原因的 {@link Throwable}。
     */
    public GenericableNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
