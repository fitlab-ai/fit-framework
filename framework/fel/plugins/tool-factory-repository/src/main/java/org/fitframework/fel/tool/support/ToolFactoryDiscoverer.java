// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.tool.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.fel.tool.ToolFactory;
import org.fitframework.fel.tool.ToolFactoryRepository;
import org.fitframework.http.client.HttpClassicClientFactory;
import org.fitframework.annotation.Component;
import org.fitframework.annotation.Fit;
import org.fitframework.broker.client.BrokerClient;
import org.fitframework.ioc.BeanFactory;
import org.fitframework.plugin.Plugin;
import org.fitframework.plugin.PluginStartedObserver;
import org.fitframework.plugin.PluginStoppingObserver;
import org.fitframework.serialization.ObjectSerializer;
import org.fitframework.value.ValueFetcher;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 表示 {@link ToolFactory} 的自动装配器。
 *
 * @author 易文渊
 * @since 2024-08-15
 */
@Component
public class ToolFactoryDiscoverer implements PluginStartedObserver, PluginStoppingObserver {
    private final ToolFactoryRepository factoryRepository;

    /**
     * 创建工具工厂的自动装配器实例。
     *
     * @param factoryRepository 表示工具工厂存储的 {@link ToolFactoryRepository}。
     * @param brokerClient 表示 FIT 调用的代理客户端的 {@link BrokerClient}。
     * @param serializer 表示 Json 序列化其的 {@link ObjectSerializer}。
     * @param factory 表示 Http 客户端工厂的 {@link HttpClassicClientFactory}。
     * @param valueFetcher 表示值的获取器的 {@link ValueFetcher}。
     */
    public ToolFactoryDiscoverer(ToolFactoryRepository factoryRepository, BrokerClient brokerClient,
            @Fit(alias = "json") ObjectSerializer serializer, HttpClassicClientFactory factory,
            ValueFetcher valueFetcher) {
        this.factoryRepository = notNull(factoryRepository, "The tool factory repository cannot be null.");
        this.factoryRepository.register(new FitToolFactory(brokerClient, serializer));
        this.factoryRepository.register(new HttpToolFactory(factory, serializer, valueFetcher));
    }

    @Override
    public void onPluginStarted(Plugin plugin) {
        scanToolFactory(plugin).forEach(this.factoryRepository::register);
    }

    @Override
    public void onPluginStopping(Plugin plugin) {
        scanToolFactory(plugin).forEach(this.factoryRepository::unregister);
    }

    private static List<ToolFactory> scanToolFactory(Plugin plugin) {
        return plugin.container()
                .factories(ToolFactory.class)
                .stream()
                .map(BeanFactory::<ToolFactory>get)
                .collect(Collectors.toList());
    }
}