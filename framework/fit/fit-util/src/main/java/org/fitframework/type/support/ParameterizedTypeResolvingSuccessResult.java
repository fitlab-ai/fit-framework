// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.type.support;

import org.fitframework.type.ParameterizedTypeResolvingResult;
import org.fitframework.util.ObjectUtils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 表示 {@link ParameterizedTypeResolvingResult} 的成功的解析结果。
 *
 * @author 梁济时
 * @since 2020-10-29
 */
public class ParameterizedTypeResolvingSuccessResult implements ParameterizedTypeResolvingResult {
    private final List<Type> parameters;

    /**
     * 使用指定的类型参数列表来初始化 {@link ParameterizedTypeResolvingSuccessResult} 的新实例。
     *
     * @param parameters 表示类型参数列表的 {@link List}{@code <}{@link Type}{@code >}。
     */
    public ParameterizedTypeResolvingSuccessResult(List<Type> parameters) {
        this.parameters = parameters;
    }

    @Override
    public boolean resolved() {
        return true;
    }

    @Override
    public List<Type> parameters() {
        return this.parameters;
    }

    @Override
    public String toString() {
        return this.parameters.stream()
                .map(parameter -> ObjectUtils.mapIfNotNull(parameter, Type::getTypeName))
                .collect(Collectors.joining(", ", "<", ">"));
    }
}
