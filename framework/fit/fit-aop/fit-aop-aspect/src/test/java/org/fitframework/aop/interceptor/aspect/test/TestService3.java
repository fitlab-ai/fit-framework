// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.aspect.test;

/**
 * 测试服务类。
 *
 * @author 季聿阶
 * @since 2022-05-14
 */
public class TestService3 extends TestService1 {
    @Override
    public String m1() {
        return "m1";
    }

    public String selfMethod() {
        return "selfMethod";
    }
}
