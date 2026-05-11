// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.datasource;

/**
 * 表示数据源访问模式的枚举。
 *
 * @author 易文渊
 * @since 2024-07-27
 */
public enum AccessMode {
    /**
     * 独占数据源。
     */
    EXCLUSIVE,

    /**
     * 共享数据源。
     */
    SHARED
}
