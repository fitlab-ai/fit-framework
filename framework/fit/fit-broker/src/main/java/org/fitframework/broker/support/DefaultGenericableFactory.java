// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.broker.ConfigurableGenericable;
import org.fitframework.broker.DynamicRouter;
import org.fitframework.broker.GenericableFactory;
import org.fitframework.broker.UniqueGenericableId;

/**
 * 表示 {@link GenericableFactory} 的默认实现。
 *
 * @author 季聿阶
 * @since 2023-03-26
 */
public class DefaultGenericableFactory implements GenericableFactory {
    private final DynamicRouter dynamicRouter;

    /**
     * 使用指定的动态路由器初始化 {@link DefaultGenericableFactory} 的新实例。
     *
     * @param dynamicRouter 表示动态路由器的 {@link DynamicRouter}。
     * @throws IllegalArgumentException 当 {@code dynamicRouter} 为 {@code null} 时。
     */
    public DefaultGenericableFactory(DynamicRouter dynamicRouter) {
        this.dynamicRouter = notNull(dynamicRouter, "The dynamic router cannot be null.");
    }

    @Override
    public ConfigurableGenericable create(String id, String version) {
        return new DefaultGenericable(this.dynamicRouter, id, version);
    }

    @Override
    public ConfigurableGenericable create(UniqueGenericableId id) {
        return this.create(id.genericableId(), id.genericableVersion());
    }
}
