// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.proxy.support.setter;

import org.fitframework.http.client.proxy.DestinationSetter;
import org.fitframework.http.client.proxy.RequestBuilder;
import org.fitframework.http.entity.Entity;
import org.fitframework.inspection.Nonnull;
import org.fitframework.util.ObjectUtils;

/**
 * 表示向消息体设置值的 {@link DestinationSetter}。
 *
 * @author 王攀博
 * @since 2024-06-07
 */
public class EntitySetter implements DestinationSetter {
    @Override
    public void set(RequestBuilder requestBuilder, Object value) {
        if (value == null) {
            return;
        }
        this.setToRequest(requestBuilder, value);
    }

    /**
     * 默认的向请求构建器中设置值。
     *
     * @param requestBuilder 表示请求构建器的 {@link RequestBuilder}。
     * @param value 表示要设置的值的 {@link Object}。
     */
    protected void setToRequest(RequestBuilder requestBuilder, @Nonnull Object value) {
        if (value instanceof Entity) {
            requestBuilder.entity(ObjectUtils.cast(value));
        }
    }
}