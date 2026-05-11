// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.annotation.convert;

import org.fitframework.util.ObjectUtils;
import org.fitframework.util.convert.Converter;

/**
 * 表示将 {@link StringValue} 的值向 {@link IntValue} 转换的程序。
 *
 * @author 梁济时
 * @since 2023-01-28
 */
public class AnnotationValueConverter implements Converter {
    @Override
    public Object convert(Object value) {
        return Integer.parseInt(ObjectUtils.cast(value));
    }
}
