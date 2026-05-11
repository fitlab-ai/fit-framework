// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.vectorstore;

import org.fitframework.fel.core.retriever.filter.Filter;
import org.fitframework.pattern.builder.BuilderFactory;

/**
 * 表示向量检索的参数。
 *
 * @author 易文渊
 * @since 2024-08-06
 */
public interface SearchOption {
    /**
     * 获取检索返回数量。
     *
     * @return 表示检索返回数量的 {@code int}。
     */
    int topK();

    /**
     * 获取标量过滤器。
     *
     * @return 表示标量过滤器的 {@link Filter}。
     */
    Filter filter();

    /**
     * 表示 {@link SearchOption} 的构建器。
     */
    interface Builder {
        /**
         * 设置检索返回数量。
         *
         * @param topK 表示检索返回数量的 {@code int}。
         * @return 表示当前构建器实例的 {@link Builder}。
         */
        Builder topK(int topK);

        /**
         * 设置标量过滤器。
         *
         * @param filter 表示标量过滤器的 {@link Filter}。
         * @return 表示当前构建器实例的 {@link Builder}。
         */
        Builder filter(Filter filter);

        /**
         * 构建 {@link SearchOption} 的实例。
         *
         * @return 表示构建实例的 {@link SearchOption}。
         */
        SearchOption build();
    }

    /**
     * 获取 {@link SearchOption} 的构建器。
     *
     * @return 表示创建成功构建器的 {@link Builder}。
     */
    static Builder custom() {
        return BuilderFactory.get(SearchOption.class, SearchOption.Builder.class).create(null);
    }
}