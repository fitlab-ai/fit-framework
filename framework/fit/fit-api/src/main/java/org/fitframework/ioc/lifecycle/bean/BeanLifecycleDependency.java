// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.lifecycle.bean;

/**
 * 表示Bean生命周期的依赖，实现了该接口的Bean不会被 {@link BeanLifecycleInterceptor} 拦截。
 *
 * @author 梁济时
 * @since 2022-05-20
 */
public interface BeanLifecycleDependency {}
