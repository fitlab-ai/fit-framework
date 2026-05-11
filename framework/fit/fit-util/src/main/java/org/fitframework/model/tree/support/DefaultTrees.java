// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.model.tree.support;

import org.fitframework.model.tree.Tree;

/**
 * 为 {@link DefaultTree} 提供工具方法。
 *
 * @author 梁济时
 * @since 2022-08-10
 */
public final class DefaultTrees {
    /**
     * 隐藏默认构造方法，避免工具类被实例化。
     */
    private DefaultTrees() {}

    /**
     * 使用路径分隔符创建树的默认实现的新实例。
     *
     * @param pathSeparator 表示路径分隔符的字符。
     * @return 表示新创建的树实例的 {@link Tree}。
     */
    public static Tree create(char pathSeparator) {
        return new DefaultTree(pathSeparator);
    }
}
