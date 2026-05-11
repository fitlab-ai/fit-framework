// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc;

/**
 * 为 {@link DependencyResolvingResult} 提供解析失败的结果。
 *
 * @author 梁济时
 * @since 2022-06-27
 */
public final class DependencyResolvingFailureResult implements DependencyResolvingResult {
    /**
     * 获取解析结果的唯一实例。
     */
    public static final DependencyResolvingFailureResult INSTANCE = new DependencyResolvingFailureResult();

    /**
     * 隐藏默认构造方法，避免单例对象被实例化。
     */
    private DependencyResolvingFailureResult() {}

    @Override
    public boolean resolved() {
        return false;
    }

    @Override
    public Object get() {
        return null;
    }

    @Override
    public String toString() {
        return "non-dependency";
    }
}
