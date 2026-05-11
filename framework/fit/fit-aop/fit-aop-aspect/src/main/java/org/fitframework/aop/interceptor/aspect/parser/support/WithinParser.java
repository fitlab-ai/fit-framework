// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.aspect.parser.support;

import org.fitframework.aop.interceptor.aspect.parser.model.PointcutSupportedType;
import org.fitframework.aop.interceptor.aspect.util.ExpressionUtils;

import java.lang.reflect.Method;

/**
 * 解析切点表达式中关键字 within 的解析器。
 * <p>用于匹配 within 表达式中包路径以及子包下任意类任意方法，支持通配符。</p>
 *
 * @author 郭龙飞
 * @since 2023-03-14
 */
public class WithinParser extends BaseParser {
    @Override
    protected PointcutSupportedType parserType() {
        return PointcutSupportedType.WITHIN;
    }

    @Override
    protected Result createConcreteParser(String content) {
        return new WithinResult(content);
    }

    class WithinResult extends BaseParser.BaseResult {
        public WithinResult(String content) {
            super(content, null);
        }

        @Override
        public boolean couldMatch(Class<?> beanClass) {
            String content = this.content().toString();
            final String replaceRegex = ExpressionUtils.expressionReplaceRegex(content);
            return beanClass.getName().matches(replaceRegex);
        }

        @Override
        public boolean match(Method method) {
            return true;
        }

        @Override
        public boolean isBinding() {
            return false;
        }
    }
}
