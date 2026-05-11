// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.aspect.test;

/**
 * 测试服务1的接口。
 *
 * @author 季聿阶
 * @since 2022-05-25
 */
public interface TestService1Interface extends AnotherTestService1Interface {
    /**
     * 测试方法1。
     *
     * @return 表示测试返回值的 {@link String}。
     */
    String m1();
}
