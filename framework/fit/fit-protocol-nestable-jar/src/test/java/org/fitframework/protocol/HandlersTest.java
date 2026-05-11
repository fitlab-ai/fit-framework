// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.protocol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 为 {@link Handlers} 提供单元测试。
 *
 * @author 杭潇
 * @since 2023-02-10
 */
@DisplayName("测试 Handlers 类")
public class HandlersTest {
    private static final String PROPERTY_KEY = "java.protocol.handler.pkgs";
    private static String ORIGIN;

    @BeforeAll
    static void setup() {
        ORIGIN = System.getProperty(PROPERTY_KEY);
    }

    @AfterAll
    static void teardownAll() {
        if (ORIGIN == null) {
            System.clearProperty(PROPERTY_KEY);
        } else {
            System.setProperty(PROPERTY_KEY, ORIGIN);
        }
    }

    @Test
    @DisplayName("调用注册方法，获取系统属性值与预期值相等")
    void invokeRegisterThenSystemPropertyIsEqualsToExpectedValue() {
        Handlers.register();
        String property = System.getProperty("java.protocol.handler.pkgs");
        assertThat(property).isEqualTo("org.fitframework.protocol");
    }

    @Test
    @DisplayName("修改调用参数的值，调用注册方法，获取系统属性值与预期值相等")
    void modifyTheParameterWhenInvokeRegisterThenSystemPropertyIsEqualsToExpectedValue() {
        System.setProperty("java.protocol.handler.pkgs", "sun.net.www.protocol");
        Handlers.register();
        String property = System.getProperty("java.protocol.handler.pkgs");
        assertThat(property).isEqualTo("org.fitframework.protocol|sun.net.www.protocol");
    }
}
