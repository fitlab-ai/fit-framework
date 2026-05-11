// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.annotation.tree;

import org.fitframework.util.convert.Converter;

/**
 * 为属性值的转换程序提供缓存。
 *
 * @author 梁济时
 * @since 2023-01-28
 */
public interface ConverterCache {
    /**
     * 获取指定类型的转换程序。
     *
     * @param converterClass 表示转换程序的类型的 {@link Class}。
     * @return 表示该类型的转换程序的 {@link Converter}。
     */
    Converter get(Class<? extends Converter> converterClass);
}
