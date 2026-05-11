// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.type.support;

import org.fitframework.type.TypeMatcher;
import org.fitframework.type.annotation.MatchTypes;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Optional;

/**
 * 为对象类型为 {@link TypeVariable} 且期望类型也为 {@link Type} 的情况提供匹配判定程序。
 *
 * @author 梁济时
 * @since 2020-10-29
 */
@MatchTypes(current = TypeVariable.class, expected = Type.class, factory = TypeVariableTypeMatcher.Factory.class)
public class TypeVariableTypeMatcher extends AbstractTypeMatcher<TypeVariable<?>, Type> {
    /**
     * 使用对象类型和上下文信息初始化 {@link TypeVariableTypeMatcher} 类的新实例。
     *
     * @param objectType 表示对象类型的 {@link TypeVariable}。
     * @param context 表示上下文信息的 {@link TypeMatcher.Context}。
     */
    public TypeVariableTypeMatcher(TypeVariable<?> objectType, Context context) {
        super(objectType, context);
    }

    @Override
    protected boolean match0(Type expectedType) {
        Optional<Type> optionalType = this.getContext().getVariableValue(this.getCurrentType().getName());
        return optionalType.map(type -> TypeMatcher.match(type, expectedType, this.getContext())).orElse(true);
    }

    /**
     * 为创建 {@link TypeVariableTypeMatcher} 实例提供工厂。
     *
     * @author 梁济时
     * @since 2020-10-29
     */
    public static class Factory implements TypeMatcher.Factory {
        @Override
        public TypeMatcher create(Type currentType, Context context) {
            return new TypeVariableTypeMatcher((TypeVariable<?>) currentType, context);
        }
    }
}
