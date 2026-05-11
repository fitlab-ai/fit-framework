// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.annotation.support;

import org.fitframework.ioc.annotation.AnnotationProperty;
import org.fitframework.ioc.annotation.AnnotationPropertyForward;
import org.fitframework.util.StringUtils;
import org.fitframework.util.convert.Converter;

/**
 * 为 {@link AnnotationPropertyForward} 提供默认实现。
 *
 * @author 梁济时
 * @since 2023-01-28
 */
public final class DefaultAnnotationPropertyForward implements AnnotationPropertyForward {
    private final AnnotationProperty target;
    private final Class<? extends Converter> converterClass;

    public DefaultAnnotationPropertyForward(AnnotationProperty target, Class<? extends Converter> converterClass) {
        this.target = target;
        this.converterClass = converterClass;
    }

    @Override
    public AnnotationProperty target() {
        return this.target;
    }

    @Override
    public Class<? extends Converter> converterClass() {
        return this.converterClass;
    }

    @Override
    public String toString() {
        return StringUtils.format("[target={0}, converter={1}]", this.target, this.converterClass.getName());
    }
}
