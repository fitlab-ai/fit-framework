// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.annotation;

/**
 * 当注解定义错误时引发的异常。
 *
 * @author 梁济时
 * @since 2022-05-03
 */
public class AnnotationDefinitionException extends RuntimeException {
    /**
     * 使用异常信息初始化 {@link AnnotationDefinitionException} 类的新实例。
     *
     * @param message 表示异常信息的 {@link String}。
     */
    public AnnotationDefinitionException(String message) {
        super(message);
    }
}
