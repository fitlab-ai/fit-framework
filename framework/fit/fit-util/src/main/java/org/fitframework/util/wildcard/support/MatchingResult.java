// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.util.wildcard.support;

import org.fitframework.util.wildcard.Pattern;
import org.fitframework.util.wildcard.Result;

/**
 * 为 {@link Result} 提供用以匹配结果的实现。
 *
 * @param <T> 表示匹配元素的类型。
 * @author 梁济时
 * @since 2022-07-28
 */
final class MatchingResult<T> extends AbstractMatcher<T> implements Result<T> {
    private final Pattern<T> pattern;
    private final boolean[] results;

    MatchingResult(Pattern<T> pattern) {
        this.pattern = pattern;
        this.results = new boolean[pattern.length() + 1];
    }

    @Override
    public Pattern<T> pattern() {
        return this.pattern;
    }

    @Override
    protected Result<T> previous() {
        return this;
    }

    @Override
    public boolean get(int index) {
        return this.results[index + 1];
    }

    void set(int index, boolean value) {
        this.results[index + 1] = value;
    }
}
