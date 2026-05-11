// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.plugin;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.util.StringUtils;

/**
 * 为插件提供键的定义。
 *
 * @author 梁济时
 * @since 2022-09-05
 */
public interface PluginKey {
    /**
     * 获取插件的分组。
     *
     * @return 表示插件分组的 {@link String}。
     */
    String group();

    /**
     * 获取插件的名称。
     *
     * @return 表示插件名称的 {@link String}。
     */
    String name();

    /**
     * 获取插件的版本。
     *
     * @return 表示插件版本的 {@link String}。
     */
    String version();

    /**
     * 返回指定插件的键的标识符。
     *
     * @param key 表示插件的键的 {@link PluginKey}。
     * @return 表示键的标识符的 {@link String}。
     * @throws IllegalArgumentException {@code key} 为 {@code null}。
     */
    static String identify(PluginKey key) {
        notNull(key, "The key to identify cannot be null.");
        return StringUtils.format("{0}:{1}:{2}", key.group(), key.name(), key.version());
    }
}
