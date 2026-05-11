// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.conf;

/**
 * 为 {@link ModifiableConfig} 提供状态变化的监听程序。
 *
 * @author 梁济时
 * @since 2022-12-14
 */
public interface ModifiableConfigListener {
    /**
     * 当配置的值发生变化时被调用的方法。
     *
     * @param config 表示值发生变化的配置的 {@link Config}。
     * @param key 表示值发生变化的配置的键的 {@link String}。
     */
    default void onValueChanged(ModifiableConfig config, String key) {};
}
