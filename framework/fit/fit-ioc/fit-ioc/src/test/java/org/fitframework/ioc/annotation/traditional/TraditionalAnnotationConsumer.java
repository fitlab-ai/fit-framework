// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.annotation.traditional;

/**
 * 为传统注解提供消费方。
 *
 * @author 梁济时
 * @since 2022-05-31
 */
@FirstLevel(TraditionalAnnotationConsumer.VALUE)
public class TraditionalAnnotationConsumer {
    /**
     * 表示注解的值。
     */
    public static final String VALUE = "Traditional";
}
