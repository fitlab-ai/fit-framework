// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.integration.mybatis.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.fitframework.integration.mybatis.MapperInvocationHandler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 为 {@link InvocationHandlerHelper} 提供单元测试。
 *
 * @author 季聿阶
 * @since 2024-08-01
 */
@DisplayName("测试 InvocationHandlerHelper")
public class InvocationHandlerHelperTest {
    @Test
    @DisplayName("使用 JDK 动态代理能力，返回正确的代理对象")
    void shouldReturnJdkProxy() {
        MapperInvocationHandler handler = mock(MapperInvocationHandler.class);
        Mapper mapper = InvocationHandlerHelper.proxyByJdk(Mapper.class, handler);
        assertThat(mapper).isNotNull();
    }

    @Test
    @DisplayName("使用 byte-buddy 动态代理能力，返回正确的代理对象")
    void shouldReturnByteBuddyProxy() {
        MapperInvocationHandler handler = mock(MapperInvocationHandler.class);
        Mapper mapper = InvocationHandlerHelper.proxyByByteBuddy(Mapper.class, handler);
        assertThat(mapper).isNotNull();
    }

    /**
     * 表示测试的 Mapper 对象。
     */
    interface Mapper {}
}
