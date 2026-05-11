// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.type.support;

import org.fitframework.type.TypeMatcher;
import org.fitframework.type.annotation.MatchTypes;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 为对象类型为 {@link Class} 且期望类型也为 {@link ParameterizedType} 的情况提供匹配判定程序。
 *
 * @author 梁济时
 * @since 2020-10-29
 */
@MatchTypes(current = Class.class, expected = ParameterizedType.class,
        factory = ClassParameterizedTypeMatcher.Factory.class)
public class ClassParameterizedTypeMatcher extends AbstractTypeMatcher<Class<?>, ParameterizedType> {
    /**
     * 使用对象类型和上下文信息初始化 {@link ClassParameterizedTypeMatcher} 类的新实例。
     *
     * @param currentType 表示对象类型的 {@link Class}。
     * @param context 表示上下文信息的 {@link TypeMatcher.Context}。
     */
    public ClassParameterizedTypeMatcher(Class<?> currentType, Context context) {
        super(currentType, context);
    }

    @Override
    protected boolean match0(ParameterizedType expectedType) {
        return this.matchSuperTypes(this.getCurrentType(), expectedType);
    }

    /**
     * 为创建 {@link ClassParameterizedTypeMatcher} 实例提供工厂。
     *
     * @author 梁济时
     * @since 2020-10-29
     */
    public static class Factory implements TypeMatcher.Factory {
        @Override
        public TypeMatcher create(Type currentType, Context context) {
            return new ClassParameterizedTypeMatcher((Class<?>) currentType, context);
        }
    }
}
