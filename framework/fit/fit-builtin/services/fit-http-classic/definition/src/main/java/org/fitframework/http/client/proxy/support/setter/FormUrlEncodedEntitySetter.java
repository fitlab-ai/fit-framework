// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.proxy.support.setter;

import static org.fitframework.inspection.Validation.notBlank;

import org.fitframework.http.client.proxy.RequestBuilder;
import org.fitframework.inspection.Nonnull;
import org.fitframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;

/**
 * 表示向消息体设置值的目标设置器。
 *
 * @author 王攀博
 * @since 2024-06-07
 */
public class FormUrlEncodedEntitySetter extends EntitySetter {
    private final String key;

    /**
     * 使用指定的键初始化 {@link FormUrlEncodedEntitySetter} 的新实例。
     *
     * @param key 表示键的 {@link String}。
     * @throws IllegalArgumentException 当 {@code key} 为 {@code null} 或空白字符串时。
     */
    public FormUrlEncodedEntitySetter(String key) {
        this.key = notBlank(key, "The key cannot be null.");
    }

    @Override
    protected void setToRequest(RequestBuilder requestBuilder, @Nonnull Object value) {
        if (value instanceof List) {
            List<?> list = ObjectUtils.cast(value);
            list.stream()
                .filter(Objects::nonNull)
                .forEach(item -> requestBuilder.formEntity(this.key, item.toString()));
        } else {
            requestBuilder.formEntity(this.key, value.toString());
        }
    }
}