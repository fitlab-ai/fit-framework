// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.annotation;

/**
 * 表示组件生效范围的枚举类。
 *
 * @author 邬涨财
 * @since 2023-07-17
 */
public enum Scope {
    /**
     * 表示组件仅在当前插件范围内生效。
     */
    PLUGIN,

    /**
     * 表示组件在全局范围生效。
     */
    GLOBAL
}
