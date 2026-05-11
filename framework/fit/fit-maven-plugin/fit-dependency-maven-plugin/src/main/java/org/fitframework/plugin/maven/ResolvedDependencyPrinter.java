// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.plugin.maven;

/**
 * {@link ResolvedDependencyPrinter} 的接口声明。
 *
 * @author 梁济时
 * @since 2020-10-09
 */
public interface ResolvedDependencyPrinter {
    /**
     * 打印解析后的依赖信息。
     *
     * @param dependency 表示依赖信息的 {@link ResolvedDependencyPrinter}。
     */
    void print(ResolvedDependency dependency);
}
