// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.annotation.repeatable;

/**
 * 为可重复注解提供消费方。
 *
 * @author 梁济时
 * @since 2022-05-31
 */
@Entry
@Value("RepeatableAnnotationConsumer")
@A1(a1 = "A1")
@A2(a2 = "A2")
public class RepeatableAnnotationConsumer {}
