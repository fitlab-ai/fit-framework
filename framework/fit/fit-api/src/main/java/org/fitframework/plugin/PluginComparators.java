// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.plugin;

import java.util.Comparator;

/**
 * 为 {@link Plugin} 提供基于启动顺序的排序程序。
 *
 * @author 季聿阶
 * @since 2023-11-23
 */
public final class PluginComparators {
    /**
     * 表示插件元数据的按启动顺序的比较程序。
     */
    public static final Comparator<Plugin> STARTUP =
            Comparator.<Plugin, Integer>comparing(plugin -> plugin.metadata().category().getId())
                    .thenComparingInt(plugin -> plugin.metadata().level());
}
