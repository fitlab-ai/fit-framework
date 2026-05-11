// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.type.support;

import org.fitframework.type.TypeMatcher;
import org.fitframework.type.annotation.MatchTypes;
import org.fitframework.util.ObjectUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 为对象类型为 {@link ParameterizedType} 且期望类型也为 {@link Class} 的情况提供匹配判定程序。
 *
 * @author 梁济时
 * @since 2020-10-29
 */
@MatchTypes(current = ParameterizedType.class, expected = Class.class,
        factory = ParameterizedTypeClassMatcher.Factory.class)
public class ParameterizedTypeClassMatcher extends AbstractTypeMatcher<ParameterizedType, Class<?>> {
    /**
     * 使用对象类型和上下文信息初始化 {@link ParameterizedTypeClassMatcher} 类的新实例。
     *
     * @param objectType 表示对象类型的 {@link ParameterizedType}。
     * @param context 表示上下文信息的 {@link TypeMatcher.Context}。
     */
    public ParameterizedTypeClassMatcher(ParameterizedType objectType, Context context) {
        super(objectType, context);
    }

    @Override
    protected boolean match0(Class<?> expectedType) {
        return expectedType.isAssignableFrom((Class<?>) this.getCurrentType().getRawType());
    }

    /**
     * 为创建 {@link ParameterizedTypeClassMatcher} 实例提供工厂。
     *
     * @author 梁济时
     * @since 2020-10-29
     */
    public static class Factory implements TypeMatcher.Factory {
        @Override
        public TypeMatcher create(Type currentType, Context context) {
            return new ParameterizedTypeClassMatcher(ObjectUtils.cast(currentType), context);
        }
    }
}
