// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.aspect.parser.support;

import org.fitframework.aop.interceptor.aspect.parser.model.PointcutSupportedType;

/**
 * 解析切点表达式中运算符与 &amp;&amp; 的解析器。
 *
 * @author 郭龙飞
 * @author 季聿阶
 * @since 2023-03-14
 */
public class AndParser extends BaseParser {
    @Override
    protected PointcutSupportedType parserType() {
        return PointcutSupportedType.AND;
    }

    @Override
    protected Result createConcreteParser(String content) {
        return new AndResult(content);
    }

    class AndResult extends BaseParser.BaseResult {
        public AndResult(String expression) {
            super(expression, null);
        }
    }
}
