// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.header.support;

import static org.fitframework.util.ObjectUtils.getIfNull;

import org.fitframework.http.header.HeaderValue;
import org.fitframework.http.header.ParameterCollection;
import org.fitframework.util.ObjectUtils;
import org.fitframework.util.StringUtils;

/**
 * 表示 {@link HeaderValue} 的默认实现。
 *
 * @author 季聿阶
 * @since 2022-09-04
 */
public class DefaultHeaderValue implements HeaderValue {
    /**
     * 消息头中属性间的分隔符。
     */
    public static final String SEPARATOR = ";";

    private final String value;
    private final ParameterCollection parameterCollection;

    /**
     * 使用指定的值和参数集合初始化 {@link DefaultHeaderValue} 的新实例。
     *
     * @param value 表示值的 {@link String}。
     * @param parameterCollection 表示参数集合的 {@link ParameterCollection}。
     */
    public DefaultHeaderValue(String value, ParameterCollection parameterCollection) {
        this.value = ObjectUtils.nullIf(value, StringUtils.EMPTY);
        this.parameterCollection = getIfNull(parameterCollection, ParameterCollection::create);
    }

    @Override
    public String value() {
        return this.value;
    }

    @Override
    public ParameterCollection parameters() {
        return this.parameterCollection;
    }

    @Override
    public String toString() {
        if (this.parameterCollection.size() > 0) {
            return this.value + SEPARATOR + this.parameterCollection;
        }
        return this.value;
    }
}
