// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.proxy.support.applier;

import org.fitframework.http.client.proxy.DestinationSetter;
import org.fitframework.http.client.proxy.PropertyValueApplier;
import org.fitframework.http.client.proxy.RequestBuilder;
import org.fitframework.http.client.proxy.support.setter.DestinationSetterInfo;
import org.fitframework.util.StringUtils;
import org.fitframework.value.ValueFetcher;

import java.util.Collections;

/**
 * 表示 {@link PropertyValueApplier} 的单个目标的实现。
 *
 * @author 王攀博
 * @since 2024-06-07
 */
public class UniqueDestinationPropertyValueApplier implements PropertyValueApplier {
    private final PropertyValueApplier applier;

    /**
     * 使用指定的设置器和值获取器初始化 {@link UniqueDestinationPropertyValueApplier} 的新实例。
     *
     * @param setter 表示设置器的 {@link DestinationSetter}。
     * @param valueFetcher 表示值获取器的 {@link ValueFetcher}。
     */
    public UniqueDestinationPropertyValueApplier(DestinationSetter setter, ValueFetcher valueFetcher) {
        this.applier = new MultiDestinationsPropertyValueApplier(Collections.singletonList(new DestinationSetterInfo(
                setter,
                StringUtils.EMPTY)),
                valueFetcher);
    }

    @Override
    public void apply(RequestBuilder requestBuilder, Object value) {
        this.applier.apply(requestBuilder, value);
    }
}
