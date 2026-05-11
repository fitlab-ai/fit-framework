// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.tool.support;

import org.fitframework.fel.tool.ToolFactory;
import org.fitframework.fel.tool.ToolFactoryRepository;
import org.fitframework.annotation.Component;
import org.fitframework.log.Logger;
import org.fitframework.util.StringUtils;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 表示 {@link ToolFactoryRepository} 的默认实现。
 *
 * @author 易文渊
 * @since 2024-08-15
 */
@Component
public class DefaultToolFactoryRepository implements ToolFactoryRepository {
    private static final Logger log = Logger.get(DefaultToolFactoryRepository.class);

    private final Map<String, ToolFactory> factoryCache = new ConcurrentHashMap<>();

    @Override
    public void register(ToolFactory factory) {
        if (factory == null) {
            return;
        }
        this.factoryCache.put(StringUtils.toUpperCase(factory.type()), factory);
        log.info("Register factory[type={}] success.", factory.type());
    }

    @Override
    public void unregister(ToolFactory factory) {
        if (factory == null) {
            return;
        }
        this.factoryCache.remove(StringUtils.toUpperCase(factory.type()));
        log.info("Unregister factory[type={}] success.", factory.type());
    }

    @Override
    public Optional<ToolFactory> match(Set<String> runnables) {
        return runnables.stream()
                .map(StringUtils::toUpperCase)
                .filter(this.factoryCache::containsKey)
                .map(this.factoryCache::get)
                .findFirst();
    }
}