// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.proxy.support.setter;

import org.fitframework.http.client.proxy.DestinationSetter;
import org.fitframework.http.client.proxy.RequestBuilder;

/**
 * 表示向 Http 请求路径设置值的 {@link DestinationSetter}。
 *
 * @author 王攀博
 * @since 2024-06-07
 */
public class PathVariableDestinationSetter extends AbstractDestinationSetter {
    /**
     * 使用指定的键初始化 {@link PathVariableDestinationSetter} 的新实例。
     *
     * @param key 表示键的 {@link String}。
     * @throws IllegalArgumentException 当 {@code key} 为 {@code null} 时。
     */
    public PathVariableDestinationSetter(String key) {
        super(key);
    }

    @Override
    public void set(RequestBuilder requestBuilder, Object pathVariable) {
        if (pathVariable != null) {
            requestBuilder.pathVariable(this.key(), pathVariable.toString());
        }
    }
}