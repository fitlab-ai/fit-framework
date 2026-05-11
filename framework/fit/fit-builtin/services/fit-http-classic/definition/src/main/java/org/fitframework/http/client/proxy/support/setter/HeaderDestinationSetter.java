// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.proxy.support.setter;

import org.fitframework.http.client.proxy.DestinationSetter;
import org.fitframework.http.client.proxy.RequestBuilder;
import org.fitframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;

/**
 * 表示向消息头设置值的 {@link DestinationSetter}。
 *
 * @author 王攀博
 * @since 2024-06-07
 */
public class HeaderDestinationSetter extends AbstractDestinationSetter {
    /**
     * 使用指定的键初始化 {@link HeaderDestinationSetter} 的新实例。
     *
     * @param key 表示键的 {@link String}。
     * @throws IllegalArgumentException 当 {@code key} 为 {@code null} 时。
     */
    public HeaderDestinationSetter(String key) {
        super(key);
    }

    @Override
    public void set(RequestBuilder requestBuilder, Object value) {
        if (value == null) {
            return;
        }
        if (value instanceof List) {
            List<?> list = ObjectUtils.cast(value);
            list.stream()
                .filter(Objects::nonNull)
                .forEach(item -> requestBuilder.header(this.key(), item.toString()));
        } else {
            requestBuilder.header(this.key(), value.toString());
        }
    }
}