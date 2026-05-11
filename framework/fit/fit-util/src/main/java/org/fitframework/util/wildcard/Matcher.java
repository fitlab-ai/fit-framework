// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.util.wildcard;

/**
 * 为模式提供匹配程序。
 *
 * @param <T> 表示待匹配的元素的类型。
 * @author 梁济时
 * @since 2022-07-28
 */
public interface Matcher<T> {
    /**
     * 匹配指定的元素。
     *
     * @param value 表示待匹配的元素的 {@link Object}。
     * @return 表示匹配的结果的 {@link Result}。
     */
    Result<T> match(T value);
}
