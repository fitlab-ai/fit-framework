// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.type.support;

import org.fitframework.type.ParameterizedTypeResolvingResult;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * 表示 {@link ParameterizedTypeResolvingResult} 的失败的解析结果。
 *
 * @author 梁济时
 * @since 2020-10-29
 */
public class ParameterizedTypeResolvingFailureResult implements ParameterizedTypeResolvingResult {
    /**
     * 提供一个静态的 {@link ParameterizedTypeResolvingResult} 的实例。
     */
    public static final ParameterizedTypeResolvingResult INSTANCE = new ParameterizedTypeResolvingFailureResult();

    private ParameterizedTypeResolvingFailureResult() {}

    @Override
    public boolean resolved() {
        return false;
    }

    @Override
    public List<Type> parameters() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return "unresolvable";
    }
}
