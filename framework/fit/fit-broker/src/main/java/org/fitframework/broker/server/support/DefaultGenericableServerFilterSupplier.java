// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.server.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.broker.server.GenericableServerFilter;
import org.fitframework.broker.server.GenericableServerFilterSupplier;
import org.fitframework.ioc.BeanContainer;
import org.fitframework.ioc.BeanFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 表示获取默认的 {@link GenericableServerFilter} 实例列表的提供器。
 *
 * @author 李金绪
 * @since 2024-08-26
 */
public class DefaultGenericableServerFilterSupplier implements GenericableServerFilterSupplier {
    @Override
    public List<GenericableServerFilter> get(BeanContainer container) {
        return notNull(container, "The bean container cannot be null.").factories(GenericableServerFilter.class)
                .stream()
                .map(BeanFactory::<GenericableServerFilter>get)
                .collect(Collectors.toList());
    }
}