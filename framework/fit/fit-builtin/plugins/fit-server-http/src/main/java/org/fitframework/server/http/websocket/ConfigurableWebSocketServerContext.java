// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.server.http.websocket;

import org.fitframework.serialization.MessageSerializer;
import org.fitframework.server.http.websocket.support.DefaultWebSocketServerContext;
import org.fitframework.flowable.Publisher;
import org.fitframework.flowable.util.worker.Worker;
import org.fitframework.serialization.TagLengthValues;
import org.fitframework.serialization.Version;

/**
 * 表示可配置的处理流式调用请求时的上下文。
 *
 * @author 何天放
 * @since 2024-04-30
 */
public interface ConfigurableWebSocketServerContext extends WebSocketServerContext {
    /**
     * 设置调用的泛服务的版本。
     *
     * @param version 表示调用的泛服务的版本的 {@link Version}。
     */
    void genericableVersion(Version version);

    /**
     * 设置泛服务调用中的扩展字段。
     *
     * @param extensions 表示调用的泛服务中扩展字段的 {@link TagLengthValues}。
     */
    void extensions(TagLengthValues extensions);

    /**
     * 设置泛服务调用的序列化方式的编码。
     *
     * @param format 表示泛服务调用的序列化方式的编码 {@code int}。
     */
    void format(int format);

    /**
     * 设置当前调用中用于辅助返回值 {@link Publisher} 的消费响应式流中元素的工具。
     * <p>仅在当前调用的返回值类型为 {@link Publisher} 时进行设置。</p>
     *
     * @param worker 表示辅助响应式流进行消费的 {@link Worker}。
     */
    void worker(Worker<?> worker);

    /**
     * 设置消息序列化器。
     *
     * @param messageSerializer 表示消息序列化器的 {@link MessageSerializer}。
     */
    void messageSerializer(MessageSerializer messageSerializer);

    /**
     * 创建一个可配置的处理流式调用请求时的上下文实例。
     *
     * @param genericableId 表示泛服务的唯一标识的 {@link String}。
     * @param fitableId 表示泛服务实现的唯一标识 {@link String}。
     * @return 表可配置的处理流式调用请求时的上下文实例的 {@link ConfigurableWebSocketServerContext}。
     */
    static ConfigurableWebSocketServerContext create(String genericableId, String fitableId) {
        return new DefaultWebSocketServerContext(genericableId, fitableId);
    }
}
