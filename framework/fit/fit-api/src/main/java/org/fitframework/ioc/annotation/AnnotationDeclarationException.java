// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.annotation;

/**
 * 当注解的声明不符合预期时引发的异常。
 *
 * @author 梁济时
 * @since 2022-05-03
 */
public class AnnotationDeclarationException extends RuntimeException {
    /**
     * 使用异常信息初始化 {@link AnnotationDeclarationException} 类的新实例。
     *
     * @param message 表示异常信息的 {@link String}。
     */
    public AnnotationDeclarationException(String message) {
        super(message);
    }

    /**
     * 使用异常信息和引发异常的原因初始化 {@link AnnotationDeclarationException} 类的新实例。
     *
     * @param message 表示异常信息的 {@link String}。
     * @param cause 表示引发异常的原因的 {@link Throwable}。
     */
    public AnnotationDeclarationException(String message, Throwable cause) {
        super(message, cause);
    }
}
