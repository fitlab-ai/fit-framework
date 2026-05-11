// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.fitframework.aop.proxy.FitProxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link FitProxyUtils} 的单元测试。
 *
 * @author 郭龙飞
 * @since 2023-02-15
 */
@DisplayName("测试 FitProxyUtils 类")
class FitProxyUtilsTest {
    @SuppressWarnings("ConstantConditions")
    @Test
    @DisplayName("提供空对象时，返回空")
    void givenNullObjectShouldReturnNull() {
        Object objNull = null;
        Class<?> targetClass = FitProxyUtils.getTargetClass(objNull);
        assertThat(targetClass).isNull();
    }

    @Test
    @DisplayName("提供对象没有 Fit 代理时，返回本身类型")
    void givenNoProxyShouldReturnSelf() {
        Integer condition = 1;
        Class<?> targetClass = FitProxyUtils.getTargetClass(condition);
        assertThat(targetClass).isEqualTo(Integer.class);
    }

    @Test
    @DisplayName("提供对象有 Fit 代理时，返回代理的实际类型")
    void givenProxyShouldReturnProxyClass() {
        FitProxy fitProxy = new ProxyTest();
        Class<?> targetClass = FitProxyUtils.getTargetClass(fitProxy);
        assertThat(targetClass).isEqualTo(Integer.class);
    }

    private static class ProxyTest implements FitProxy {
        @Override
        public Class<?> $fit$getActualClass() {
            return Integer.class;
        }
    }
}