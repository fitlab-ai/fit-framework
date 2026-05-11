// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.serialization;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.serialization.MessageSerializer;
import org.fitframework.broker.SerializationService;
import org.fitframework.ioc.BeanContainer;
import org.fitframework.ioc.BeanFactory;
import org.fitframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 为 {@link SerializationService} 提供默认实现。
 *
 * @author 梁济时
 * @author 季聿阶
 * @since 2020-11-12
 */
public class DefaultSerializationService implements SerializationService {
    private final BeanContainer container;
    private final Map<Method, List<Integer>> cachedSupportedFormatsMapping = new HashMap<>();

    /**
     * 使用指定的容器初始化 {@link DefaultSerializationService} 的新实例。
     *
     * @param container 表示容器的 {@link BeanContainer}。
     * @throws IllegalArgumentException 当 {@code container} 为 {@code null} 时。
     */
    public DefaultSerializationService(BeanContainer container) {
        this.container = notNull(container, "The bean container cannot be null.");
    }

    @Override
    public Optional<MessageSerializer> get(int format) {
        return this.getMessageSerializers().stream().filter(serializer -> serializer.getFormat() == format).findFirst();
    }

    @Override
    public List<Integer> getSupportedFormats(Method genericableMethod) {
        List<Integer> cachedSupportedFormats = this.cachedSupportedFormatsMapping.get(genericableMethod);
        if (CollectionUtils.isNotEmpty(cachedSupportedFormats)) {
            return cachedSupportedFormats;
        }
        List<Integer> supportedFormats = this.resolveSupportedSerialization(genericableMethod);
        if (CollectionUtils.isNotEmpty(supportedFormats)) {
            this.cachedSupportedFormatsMapping.put(genericableMethod, supportedFormats);
        }
        return supportedFormats;
    }

    private List<Integer> resolveSupportedSerialization(Method method) {
        return this.getMessageSerializers()
                .stream()
                .filter(serializer -> serializer.isSupported(method))
                .map(MessageSerializer::getFormat)
                .collect(Collectors.toList());
    }

    private List<MessageSerializer> getMessageSerializers() {
        return this.container.all(MessageSerializer.class)
                .stream()
                .map(BeanFactory::<MessageSerializer>get)
                .collect(Collectors.toList());
    }
}
