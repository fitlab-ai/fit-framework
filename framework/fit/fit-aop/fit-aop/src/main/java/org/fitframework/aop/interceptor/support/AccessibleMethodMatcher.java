// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.support;

import org.fitframework.aop.interceptor.MethodMatcher;
import org.fitframework.inspection.Nonnull;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 表示所有可访问方法的匹配器。
 *
 * @author 季聿阶
 * @since 2022-05-28
 */
public class AccessibleMethodMatcher implements MethodMatcher {
    /** 表示 {@link AccessibleMethodMatcher} 的单例。 */
    public static final MethodMatcher INSTANCE = new AccessibleMethodMatcher();

    private AccessibleMethodMatcher() {}

    @Override
    public MatchResult match(@Nonnull Method method) {
        int modifiers = method.getModifiers();
        if (Modifier.isFinal(modifiers) || Modifier.isPrivate(modifiers)) {
            return new DefaultMatchResult(false);
        }
        return new DefaultMatchResult(true);
    }
}
