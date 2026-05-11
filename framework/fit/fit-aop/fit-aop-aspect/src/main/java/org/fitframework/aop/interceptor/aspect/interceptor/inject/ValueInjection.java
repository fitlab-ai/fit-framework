// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.aspect.interceptor.inject;

/**
 * 返回值或异常的注入信息。
 *
 * @author 季聿阶
 * @since 2022-05-19
 */
public class ValueInjection {
    private final String name;
    private final Object value;

    /**
     * 使用注入信息的键值对实例化 {@link ValueInjection}。
     *
     * @param name 表示注入信息的键的 {@link String}。
     * @param value 表示注入信息的值的 {@link Object}。
     */
    public ValueInjection(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    /**
     * 获取注入信息的键的 {@link String}。
     *
     * @return 表示注入信息的键的 {@link String}。
     */
    public String getName() {
        return this.name;
    }

    /**
     * 获取注入信息的值的 {@link Object}。
     *
     * @return 表示注入信息的值的 {@link Object}。
     */
    public Object getValue() {
        return this.value;
    }
}
