// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.util.wildcard.support;

import org.fitframework.util.wildcard.Pattern;
import org.fitframework.util.wildcard.PatternBuilder;
import org.fitframework.util.wildcard.SymbolClassifier;
import org.fitframework.util.wildcard.SymbolMatcher;
import org.fitframework.util.wildcard.SymbolSequence;

/**
 * 为 {@link PatternBuilder} 提供默认实现。
 *
 * @param <T> 表示模式匹配的元素的类型。
 * @author 梁济时
 * @since 2022-07-29
 */
public class DefaultPatternBuilder<T> implements PatternBuilder<T> {
    private SymbolSequence<T> pattern;
    private SymbolClassifier<T> symbolClassifier;
    private SymbolMatcher<T> symbolMatcher;

    private final SymbolConfigurator symbols;

    /**
     * 初始化 {@link DefaultPatternBuilder} 类的新实例。
     */
    public DefaultPatternBuilder() {
        this.symbols = new SymbolConfigurator();
    }

    @Override
    public PatternBuilder<T> pattern(SymbolSequence<T> pattern) {
        this.pattern = pattern;
        return this;
    }

    @Override
    public PatternBuilder.SymbolConfigurator<T> symbol() {
        return this.symbols;
    }

    private class SymbolConfigurator implements PatternBuilder.SymbolConfigurator<T> {
        @Override
        public PatternBuilder<T> classifier(SymbolClassifier<T> classifier) {
            DefaultPatternBuilder.this.symbolClassifier = classifier;
            return DefaultPatternBuilder.this;
        }

        @Override
        public PatternBuilder<T> matcher(SymbolMatcher<T> matcher) {
            DefaultPatternBuilder.this.symbolMatcher = matcher;
            return DefaultPatternBuilder.this;
        }
    }

    @Override
    public Pattern<T> build() {
        return new DefaultPattern<>(this.pattern, this.symbolClassifier, this.symbolMatcher);
    }
}
