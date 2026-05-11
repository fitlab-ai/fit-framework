// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.proxy.support.setter;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.client.proxy.RequestBuilder;
import org.fitframework.inspection.Nonnull;

/**
 * 表示向消息体设置值的目标设置器。
 *
 * @author 王攀博
 * @since 2024-06-07
 */
public class ObjectEntitySetter extends EntitySetter {
    private final String propertyPath;

    /**
     * 使用指定的属性路径初始化 {@link ObjectEntitySetter} 的新实例。
     *
     * @param propertyPath 表示属性路径的 {@link String}。
     * @throws IllegalArgumentException 当 {@code propertyPath} 为 {@code null} 时。
     */
    public ObjectEntitySetter(String propertyPath) {
        this.propertyPath = notNull(propertyPath, "The property path cannot be null.");
    }

    @Override
    protected void setToRequest(RequestBuilder requestBuilder, @Nonnull Object value) {
        requestBuilder.jsonEntity(this.propertyPath, value);
    }
}