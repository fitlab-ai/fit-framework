// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.pattern;

import java.util.List;

/**
 * 表示数据切分算子。
 *
 * @param <D> 表示切分算子入参的泛型。
 * @author 刘信宏
 * @since 2024-04-28
 */
@FunctionalInterface
public interface Splitter<D> extends Pattern<D, List<D>> {
    /**
     * 将输入数据进入切分。
     *
     * @param data 表示输入数据的 {@link D}。
     * @return 表示切分完毕数据列表的 {@link List}{@code <}{@link D}{@code >}。
     */
    List<D> split(D data);

    @Override
    default List<D> invoke(D data) {
        return this.split(data);
    }
}
