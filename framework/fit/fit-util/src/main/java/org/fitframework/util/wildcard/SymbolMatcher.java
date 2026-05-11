// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.util.wildcard;

/**
 * 为符号提供匹配程序。
 *
 * @param <T> 表示符号的类型。
 * @author 梁济时
 * @since 2022-07-28
 */
@FunctionalInterface
public interface SymbolMatcher<T> {
    /**
     * 匹配两个符号。
     *
     * @param pattern 表示待匹配的模式符号的 {@link Object}。
     * @param value 表示待匹配的值符号的 {@link Object}。
     * @return 如成功匹配，则为 {@code true}；否则为 {@code false}。
     */
    boolean match(T pattern, T value);
}
