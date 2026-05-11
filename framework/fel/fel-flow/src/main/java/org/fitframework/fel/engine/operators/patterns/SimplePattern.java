// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.engine.operators.patterns;

import org.fitframework.fel.core.pattern.Pattern;
import org.fitframework.inspection.Validation;

import java.util.function.Function;

/**
 * 委托单元的简单实现。
 *
 * @author 刘信宏
 * @since 2024-06-11
 */
public class SimplePattern<I, O> implements Pattern<I, O> {
    private final Function<I, O> func;

    /**
     * 创建一个委托单元。
     *
     * @param func 委托函数。
     */
    public SimplePattern(Function<I, O> func) {
        this.func = Validation.notNull(func, "The action function cannot be null.");
    }

    @Override
    public O invoke(I input) {
        Validation.notNull(input, "The input data cannot be null.");
        return this.func.apply(input);
    }
}
