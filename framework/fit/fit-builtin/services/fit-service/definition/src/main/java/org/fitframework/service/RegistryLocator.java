// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.service;

import org.fitframework.broker.Target;

import java.util.List;

/**
 * 表示注册中心的定位器。
 *
 * @author 季聿阶
 * @since 2022-09-12
 */
public interface RegistryLocator {
    /**
     * 获取注册中心的地址列表。
     *
     * @return 表示注册中心的地址列表的 {@link List}{@code <}{@link Target}{@code >}。
     */
    List<Target> targets();
}
