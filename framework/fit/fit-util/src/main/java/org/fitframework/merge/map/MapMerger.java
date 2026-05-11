// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.merge.map;

import org.fitframework.merge.Merger;

import java.util.List;
import java.util.Map;

/**
 * {@link Map} 对象的合并器。
 *
 * @author 季聿阶
 * @since 2022-08-02
 */
public interface MapMerger<K, V> extends Merger<Map<K, V>> {
    /**
     * 获取冲突点的位置信息。
     *
     * @return 表示冲突点位置信息的 {@link List}{@code <}{@link Object}{@code >}。
     */
    List<Object> location();
}
