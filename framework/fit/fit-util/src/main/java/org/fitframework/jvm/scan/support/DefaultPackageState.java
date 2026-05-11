// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.jvm.scan.support;

import org.fitframework.jvm.scan.PackageState;

import java.util.HashSet;
import java.util.Set;

/**
 * 为 {@link PackageState} 提供默认实现。
 *
 * @author 梁济时
 * @since 2023-02-01
 */
public final class DefaultPackageState implements PackageState {
    private final Set<String> basePackages;

    /**
     * 初始化 {@link DefaultPackageState} 类的新实例。
     */
    public DefaultPackageState() {
        this.basePackages = new HashSet<>();
    }

    @Override
    public boolean get(String basePackage) {
        return this.basePackages.contains(basePackage);
    }

    @Override
    public void set(String basePackage) {
        this.basePackages.add(basePackage);
    }
}
