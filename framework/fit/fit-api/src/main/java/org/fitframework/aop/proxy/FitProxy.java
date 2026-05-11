// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.proxy;

/**
 * FIT 生成的代理的统一接口，用于提供方便的方法。
 *
 * @author 季聿阶
 * @since 2022-06-10
 */
public interface FitProxy {
    /**
     * 获取代理对象的真正类型。
     *
     * @return 表示代理对象的真正类型的 {@link Class}{@code <}{@link Object}{@code >}。
     */
    Class<?> $fit$getActualClass();
}
