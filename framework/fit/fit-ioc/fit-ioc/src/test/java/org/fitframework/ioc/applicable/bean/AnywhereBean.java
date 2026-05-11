// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.applicable.bean;

import org.fitframework.annotation.ApplicableScope;
import org.fitframework.annotation.Component;
import org.fitframework.ioc.BeanApplicableScope;

/**
 * 定义应用范围为 {@link BeanApplicableScope#ANYWHERE} 的 Bean。
 *
 * @author 梁济时
 * @since 2022-08-30
 */
@Component
@ApplicableScope(BeanApplicableScope.ANYWHERE)
public class AnywhereBean {}
