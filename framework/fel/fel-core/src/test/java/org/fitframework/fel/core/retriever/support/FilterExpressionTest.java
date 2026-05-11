// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.retriever.support;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.fitframework.fel.core.retriever.filter.ExpressionParser;
import org.fitframework.fel.core.retriever.filter.Filter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 表示 {@link Filter} 的测试集。
 *
 * @author 易文渊
 * @since 2024-08-10
 */
@DisplayName("测试 Filter")
public class FilterExpressionTest {
    private final ExpressionParser parser = new TestExpressionParser();

    @Test
    @DisplayName("测试 eq 表达式")
    void testEqExpression() {
        Filter filter = Filter.eq("country", "china");
        assertThat(parser.parse(filter.expression())).isEqualTo("country EQ \"china\"");
    }

    @Test
    @DisplayName("测试 in 表达式")
    void testInExpression() {
        Filter filter = Filter.in("color", Arrays.asList("blue", "black", "white"));
        assertThat(parser.parse(filter.expression())).isEqualTo("color IN [\"blue\",\"black\",\"white\"]");
    }

    @Test
    @DisplayName("测试 and 表达式")
    void testAndExpression() {
        Filter filter = Filter.eq("country", "china").and(Filter.ne("profession", "doctor"));
        assertThat(parser.parse(filter.expression())).isEqualTo("country EQ \"china\" AND profession NE \"doctor\"");
    }
}