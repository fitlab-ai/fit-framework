// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.plugin;

import org.fitframework.util.CodeableEnum;

/**
 * 为插件提供类别。
 *
 * @author 梁济时
 * @since 2022-06-06
 */
public enum PluginCategory implements CodeableEnum<PluginCategory> {
    /**
     * 表示系统插件。
     */
    SYSTEM(1, "system"),

    /**
     * 表示用户插件。
     */
    USER(2, "user");

    private final Integer id;
    private final String code;

    PluginCategory(Integer id, String code) {
        this.id = id;
        this.code = code;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String toString() {
        return this.getCode();
    }
}
