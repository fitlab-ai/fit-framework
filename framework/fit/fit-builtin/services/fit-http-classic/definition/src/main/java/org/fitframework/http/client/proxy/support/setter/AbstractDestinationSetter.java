// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.proxy.support.setter;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.client.proxy.DestinationSetter;

/**
 * 表示设置键值对的键。
 *
 * @author 王攀博
 * @since 2024-06-08
 */
public abstract class AbstractDestinationSetter implements DestinationSetter {
    private final String key;

    /**
     * 使用指定的键初始化 {@link AbstractDestinationSetter} 的新实例。
     *
     * @param key 表示键的 {@link String}。
     * @throws IllegalArgumentException 当 {@code key} 为 {@code null} 时。
     */
    public AbstractDestinationSetter(String key) {
        this.key = notNull(key, "The key cannot be null.");
    }

    /**
     * 获取键值对的键。
     *
     * @return 表示键值对的键的 {@link String}。
     */
    protected String key() {
        return this.key;
    }
}
