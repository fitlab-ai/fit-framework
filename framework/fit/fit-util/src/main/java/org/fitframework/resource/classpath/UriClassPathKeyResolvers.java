// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.resource.classpath;

import org.fitframework.resource.classpath.support.FileUriClassPathKeyResolver;

/**
 * 为 {@link UriClassPathKeyResolver} 提供工具方法。
 *
 * @author 梁济时
 * @since 2022-07-27
 */
final class UriClassPathKeyResolvers {
    /**
     * 获取 {@link UriClassPathKeyResolver} 的当前实现。
     */
    static final UriClassPathKeyResolver CURRENT =
            UriClassPathKeyResolver.combine(FileUriClassPathKeyResolver.INSTANCE);

    /**
     * 隐藏默认构造方法，避免工具类被实例化。
     */
    private UriClassPathKeyResolvers() {}
}
