// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.aspect.parser.support;

import static org.fitframework.inspection.Validation.isFalse;
import static org.fitframework.inspection.Validation.notBlank;

import org.fitframework.aop.interceptor.aspect.parser.ExpressionParser;
import org.fitframework.aop.interceptor.aspect.parser.PointcutParameter;
import org.fitframework.aop.interceptor.aspect.parser.PointcutParser;
import org.fitframework.aop.interceptor.aspect.parser.model.PointcutSupportedType;
import org.fitframework.aop.interceptor.aspect.util.ExpressionUtils;
import org.fitframework.util.ObjectUtils;
import org.fitframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link PointcutParser} 默认实现。
 *
 * @author 郭龙飞
 * @since 2023-03-14
 */
public class DefaultPointcutParser implements PointcutParser {
    private final List<String> expressions;
    private final Class<?> aspectClass;
    private final PointcutParameter[] parameters;
    private final List<ExpressionParser> expressionParsers;

    /**
     * 使用指定的切点表达式、切面类和切点参数初始化 {@link DefaultPointcutParser} 的新实例。
     *
     * @param pointcut 表示切点表达式的 {@link String}。
     * @param aspectClass 表示切面类的 {@link Class}{@code <?>}。
     * @param parameters 表示切点参数的 {@link PointcutParameter}{@code []}。
     */
    public DefaultPointcutParser(String pointcut, Class<?> aspectClass, PointcutParameter[] parameters) {
        this.expressions = ExpressionUtils.expressionSplit(pointcut);
        this.aspectClass = aspectClass;
        this.parameters = parameters;
        this.expressionParsers = this.getParserList(aspectClass, parameters);
    }

    @Override
    public List<ExpressionParser.Result> parse() {
        List<ExpressionParser.Result> results = new ArrayList<>();
        for (String expression : this.expressions) {
            isFalse(expression.startsWith("(") && expression.endsWith(")"),
                    "UnSupported '(' ')' operators. [expression={0}]",
                    expression);
            ExpressionParser expressionParser = this.expressionParsers.stream()
                    .filter(parser -> parser.couldParse(expression))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(StringUtils.format(
                            "The expression value format error. [expression={0}]",
                            expression)));
            ExpressionParser.Result result = expressionParser.parse(expression);
            if (result.type() == PointcutSupportedType.REFERENCE) {
                results.add(LeftBracketParser.getResult());
                String pointcut = ObjectUtils.cast(result.content());
                notBlank(pointcut, "The expression value cannot be blank. [pointcut={0}]", expression);
                DefaultPointcutParser pointcutParser =
                        new DefaultPointcutParser(pointcut, this.aspectClass, this.parameters);
                List<ExpressionParser.Result> resultList = pointcutParser.parse();
                results.addAll(resultList);
                results.add(RightBracketParser.getResult());
            } else {
                results.add(result);
            }
        }
        return results;
    }

    private List<ExpressionParser> getParserList(Class<?> aspectClass, PointcutParameter[] parameters) {
        return PointcutParserFactory.create(aspectClass, parameters).build();
    }
}
