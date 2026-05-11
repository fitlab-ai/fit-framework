// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.util.wildcard;

/**
 * 为元素匹配提供结果。
 *
 * @param <T> 表示元素的类型。
 * @author 梁济时
 * @since 2022-07-28
 */
public interface Result<T> extends Matcher<T> {
    /**
     * 获取产生结果的模式。
     *
     * @return 表示匹配模式的 {@link Pattern}。
     */
    Pattern<T> pattern();

    /**
     * 获取指定索引处的结果。
     *
     * @param index 表示结果的索引的32位整数。
     * @return 若该索引处的内容匹配成功，则为 {@code true}；否则为 {@code false}。
     */
    boolean get(int index);

    /**
     * 获取一个值，该值指示是否匹配成功。
     *
     * @return 若匹配成功，则为 {@code true}；否则为 {@code false}。
     */
    default boolean matched() {
        return this.get(this.pattern().length() - 1);
    }
}
