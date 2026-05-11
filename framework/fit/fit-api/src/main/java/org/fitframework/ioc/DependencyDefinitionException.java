// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc;

/**
 * 当依赖的定义不正确时抛出的异常。
 *
 * @author 梁济时
 * @since 2022-11-29
 */
public class DependencyDefinitionException extends DependencyException {
    /**
     * 使用异常信息初始化 {@link DependencyDefinitionException} 类的新实例。
     *
     * @param message 表示异常信息的 {@link String}。
     */
    public DependencyDefinitionException(String message) {
        super(message);
    }

    /**
     * 使用引发异常的原因初始化 {@link DependencyDefinitionException} 类的新实例。
     *
     * @param cause 表示引发异常的原因的 {@link Throwable}。
     */
    public DependencyDefinitionException(Throwable cause) {
        super(cause);
    }

    /**
     * 使用异常信息和引发异常的原因初始化 {@link DependencyDefinitionException} 类的新实例。
     *
     * @param message 表示异常信息的 {@link String}。
     * @param cause 表示引发异常的原因的 {@link Throwable}。
     */
    public DependencyDefinitionException(String message, Throwable cause) {
        super(message, cause);
    }
}
