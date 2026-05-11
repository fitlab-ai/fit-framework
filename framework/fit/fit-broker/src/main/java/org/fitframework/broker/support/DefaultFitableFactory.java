// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.broker.ConfigurableFitable;
import org.fitframework.broker.Fitable;
import org.fitframework.broker.FitableFactory;
import org.fitframework.broker.LoadBalancer;
import org.fitframework.broker.TargetLocator;
import org.fitframework.broker.UniqueFitableId;
import org.fitframework.ioc.BeanContainer;

/**
 * 表示 {@link FitableFactory} 的默认实现。
 *
 * @author 季聿阶
 * @since 2023-03-24
 */
public class DefaultFitableFactory implements FitableFactory {
    private final BeanContainer container;
    private final LoadBalancer loadBalancer;
    private final TargetLocator targetLocator;

    /**
     * 使用指定的容器、负载均衡器和目标定位器初始化 {@link DefaultFitableFactory} 的新实例。
     *
     * @param container 表示容器的 {@link BeanContainer}。
     * @param loadBalancer 表示负载均衡器的 {@link LoadBalancer}。
     * @param targetLocator 表示目标定位器的 {@link TargetLocator}。
     * @throws IllegalArgumentException 当 {@code container}、{@code loadBalancer} 或 {@code targetLocator} 为 {@code null}
     * 时。
     */
    public DefaultFitableFactory(BeanContainer container, LoadBalancer loadBalancer, TargetLocator targetLocator) {
        this.container = notNull(container, "The bean container cannot be null.");
        this.loadBalancer = notNull(loadBalancer, "The load balancer cannot be null.");
        this.targetLocator = notNull(targetLocator, "The target locator cannot be null.");
    }

    @Override
    public ConfigurableFitable create(String id, String version) {
        return new DefaultFitable(this.container, this.loadBalancer, this.targetLocator, id, version);
    }

    @Override
    public ConfigurableFitable create(UniqueFitableId id) {
        notNull(id, "The unique fitable id cannot be null.");
        return this.create(id.fitableId(), id.fitableVersion());
    }

    @Override
    public ConfigurableFitable create(Fitable fitable) {
        notNull(fitable, "The fitable cannot be null.");
        return this.create(fitable.id(), fitable.version())
                .aliases(fitable.aliases().all())
                .tags(fitable.tags().all())
                .degradationFitableId(fitable.degradationFitableId())
                .genericable(fitable.genericable());
    }
}
