// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.client.Client;
import org.fitframework.broker.Fitable;
import org.fitframework.broker.InvocationContext;
import org.fitframework.broker.LoadBalancer;
import org.fitframework.broker.SerializationService;
import org.fitframework.broker.Target;
import org.fitframework.broker.TargetLocator;
import org.fitframework.broker.UniqueFitableId;
import org.fitframework.broker.client.Invoker;
import org.fitframework.broker.client.TargetNotFoundException;
import org.fitframework.broker.client.filter.loadbalance.EnvironmentFilter;
import org.fitframework.broker.client.filter.loadbalance.FirstMatchedEnvironmentFilter;
import org.fitframework.broker.client.filter.loadbalance.ProtocolAndFormatSupportedFilter;
import org.fitframework.ioc.BeanContainer;
import org.fitframework.ioc.BeanFactory;
import org.fitframework.log.Logger;
import org.fitframework.util.CollectionUtils;
import org.fitframework.util.LazyLoader;
import org.fitframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 表示 {@link LoadBalancer} 的默认实现。
 *
 * @author 季聿阶
 * @since 2023-03-28
 */
public class DefaultLoadBalancer implements LoadBalancer {
    private static final Logger log = Logger.get(DefaultLoadBalancer.class);

    private final BeanContainer container;
    private final LazyLoader<List<Client>> clientsLoader;
    private final SerializationService serializationService;
    private final TargetLocator targetLocator;

    /**
     * 使用指定的容器、序列化服务和地址定位服务初始化 {@link DefaultLoadBalancer} 的新实例。
     *
     * @param container 表示容器的 {@link BeanContainer}。
     * @param serializationService 表示序列化服务的 {@link SerializationService}。
     * @param targetLocator 表示地址定位服务的 {@link TargetLocator}。
     * @throws IllegalArgumentException 当 {@code container}、{@code serializationService} 或 {@code targetLocator} 为
     * {@code null} 时。
     */
    public DefaultLoadBalancer(BeanContainer container, SerializationService serializationService,
            TargetLocator targetLocator) {
        this.container = notNull(container, "The bean container cannot be null.");
        this.clientsLoader = new LazyLoader<>(this::getClients);
        this.serializationService = notNull(serializationService, "The serialization service cannot be null.");
        this.targetLocator = notNull(targetLocator, "The target locator cannot be null.");
    }

    @Override
    public List<Target> balance(Fitable fitable, InvocationContext context, Object[] args) {
        Invoker.Filter filter = Invoker.Filter.combine(this.getFirstMatchedEnvironmentFilter(fitable, context),
                this.getProtocolAndFormatSupportedFilter(context),
                context.loadBalanceFilter());
        return this.filterCandidateTargets(filter, fitable, context, this.getTargets(fitable.toUniqueId()));
    }

    private Invoker.Filter getFirstMatchedEnvironmentFilter(Fitable fitable, InvocationContext context) {
        if (context.specifiedEnvironment() != null) {
            log.debug("Environment is specified. [id={}, environment={}]",
                    fitable.toUniqueId(),
                    context.specifiedEnvironment());
            return new EnvironmentFilter(context.specifiedEnvironment());
        }
        return new FirstMatchedEnvironmentFilter(context.environmentPrioritySequence());
    }

    private Invoker.Filter getProtocolAndFormatSupportedFilter(InvocationContext context) {
        return new ProtocolAndFormatSupportedFilter(this.clientsLoader.get(),
                this.serializationService,
                context.protocol(),
                context.format());
    }

    private List<Client> getClients() {
        return this.container.all(Client.class).stream().map(BeanFactory::<Client>get).collect(Collectors.toList());
    }

    private List<Target> filterCandidateTargets(Invoker.Filter filter, Fitable fitable, InvocationContext context,
            List<Target> toFilterTargets) {
        List<Target> filteredTargets =
                filter.filter(fitable, context.localWorkerId(), toFilterTargets, context.filterExtensions());
        filteredTargets = this.filterWithOtherFitables(context, filteredTargets);
        if (CollectionUtils.isEmpty(filteredTargets)) {
            String message = StringUtils.format("No matched fitable targets left after loadbalance. [id={0}]",
                    fitable.toUniqueId());
            TargetNotFoundException exception = new TargetNotFoundException(message);
            exception.associateFitable(fitable.genericable().id(), fitable.id());
            throw exception;
        }
        return filteredTargets;
    }

    private List<Target> filterWithOtherFitables(InvocationContext context, List<Target> targets) {
        List<Target> intersection = targets;
        for (UniqueFitableId id : context.loadBalanceWith()) {
            intersection = this.intersect(intersection, this.getTargets(id));
        }
        return intersection;
    }

    private List<Target> intersect(List<Target> targets, List<Target> toFilterTargets) {
        Set<String> workerIds = toFilterTargets.stream().map(Target::workerId).collect(Collectors.toSet());
        return targets.stream().filter(target -> workerIds.contains(target.workerId())).collect(Collectors.toList());
    }

    private List<Target> getTargets(UniqueFitableId id) {
        return this.targetLocator.lookup(id);
    }
}
