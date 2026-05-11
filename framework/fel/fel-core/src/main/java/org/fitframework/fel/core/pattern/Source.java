// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.pattern;

import org.fitframework.fel.core.document.Document;

import java.util.List;

/**
 * 表示数据源的实体，用于加载文档。
 *
 * @param <I> 表示输入参数的泛型。
 * @author 易文渊
 * @since 2024-08-06
 */
@FunctionalInterface
public interface Source<I> extends Pattern<I, List<Document>> {
    /**
     * 根据输入参数加载文档。
     *
     * @param input 表示输入参数的 {@link I}。
     * @return 表示加载文档列表的 {@link List}{@code <}{@link Document}{@code >}。
     */
    List<Document> load(I input);

    @Override
    default List<Document> invoke(I input) {
        return this.load(input);
    }
}