// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.aspect.parser.support;

import org.fitframework.aop.interceptor.aspect.parser.model.PointcutSupportedType;

/**
 * 解析切点表达式中运算符 '(' 的解析器。用于 pointcut 解析时自动添加，解决多个 pointcut 运算优先级问题。
 *
 * @author 白鹏坤
 * @since 2023-03-31
 */
public class LeftBracketParser extends BaseParser {
    @Override
    protected PointcutSupportedType parserType() {
        return PointcutSupportedType.LEFT_BRACKET;
    }

    @Override
    public Result createConcreteParser(String content) {
        return new LeftBracketResult(content);
    }

    /**
     * 左括号解析结果类。
     */
    public class LeftBracketResult extends BaseParser.BaseResult {
        /**
         * 使用指定的表达式初始化 {@link LeftBracketResult} 的新实例。
         *
         * @param expression 表示表达式的 {@link String}。
         */
        public LeftBracketResult(String expression) {
            super(expression, null);
        }
    }

    /**
     * 创建解析结果实例。
     *
     * @return 解析结果实例的 {@link Result}。
     */
    public static Result getResult() {
        return new LeftBracketParser().createConcreteParser(PointcutSupportedType.LEFT_BRACKET.getValue());
    }
}
