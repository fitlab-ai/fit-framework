// SPDX-License-Identifier: MIT
// Copyright (c) 2025-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.tool.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.fel.tool.Tool;
import org.fitframework.fel.tool.ToolFactory;
import org.fitframework.broker.client.BrokerClient;
import org.fitframework.inspection.Nonnull;
import org.fitframework.serialization.ObjectSerializer;

/**
 * 表示创建参数工具的工厂。
 *
 * @author 王攀博
 * @since 2024-04-23
 */
public class FitToolFactory implements ToolFactory {
    private final BrokerClient brokerClient;
    private final ObjectSerializer serializer;

    /**
     * 创建 FIT 工厂工厂的实例。
     *
     * @param brokerClient 表示服务调用代理客户端的 {@link BrokerClient}。
     * @param serializer 表示对象序列化器的 {@link ObjectSerializer}。
     * @throws IllegalArgumentException 当 {@code brokerClient}、{@code serializer} 为 {@code null} 时。
     */
    public FitToolFactory(BrokerClient brokerClient, ObjectSerializer serializer) {
        this.brokerClient = notNull(brokerClient, "The broker client cannot be null.");
        this.serializer = notNull(serializer, "The serializer cannot be null.");
    }

    @Nonnull
    @Override
    public String type() {
        return FitTool.TYPE;
    }

    @Override
    public Tool create(Tool.Info itemInfo, Tool.Metadata metadata) {
        return new FitTool(this.brokerClient, this.serializer, itemInfo, metadata);
    }
}
