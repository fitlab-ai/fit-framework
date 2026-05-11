// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.tool.support;

import static org.fitframework.inspection.Validation.notBlank;
import static org.fitframework.util.ObjectUtils.cast;

import org.fitframework.fel.core.tool.ToolInfo;
import org.fitframework.fel.tool.ToolInfoEntity;
import org.fitframework.fel.tool.service.ToolChangedObserver;
import org.fitframework.fel.tool.service.ToolChangedObserverRegistry;
import org.fitframework.fel.tool.service.ToolRepository;
import org.fitframework.annotation.Component;
import org.fitframework.log.Logger;
import org.fitframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * A simple implementation of the {@link ToolRepository} interface.
 *
 * @author 易文渊
 * @author 杭潇
 * @since 2024-08-15
 */
@Component
public class SimpleToolRepository implements ToolRepository, ToolChangedObserverRegistry {
    private static final Logger log = Logger.get(SimpleToolRepository.class);

    private final Map<String, ToolInfoEntity> toolCache = new ConcurrentHashMap<>();
    private final List<ToolChangedObserver> toolChangedObservers = new ArrayList<>();

    @Override
    public void register(ToolChangedObserver observer) {
        if (observer != null) {
            this.toolChangedObservers.add(observer);
        }
    }

    @Override
    public void unregister(ToolChangedObserver observer) {
        if (observer != null) {
            this.toolChangedObservers.remove(observer);
        }
    }

    @Override
    public void addTool(ToolInfoEntity tool) {
        if (tool == null) {
            return;
        }
        String uniqueName = ToolInfo.identify(tool);
        this.toolCache.put(uniqueName, tool);
        log.info("Register tool[uniqueName={}] success.", uniqueName);
        Map<String, Object> parameters = cast(tool.schema().get("parameters"));
        this.toolChangedObservers.forEach(observer -> {
            try {
                observer.onToolAdded(uniqueName, tool.description(), parameters);
            } catch (Exception e) {
                log.error("Failed to notify observer of tool added. [observer={}, uniqueName={}, error={}]",
                        observer.getClass().getName(),
                        uniqueName,
                        e.getMessage(),
                        e);
            }
        });
    }

    @Override
    public void deleteTool(String namespace, String toolName) {
        if (StringUtils.isBlank(namespace) || StringUtils.isBlank(toolName)) {
            return;
        }
        String uniqueName = ToolInfo.identify(namespace, toolName);
        this.toolCache.remove(uniqueName);
        log.info("Unregister tool[uniqueName={}] success.", uniqueName);
        this.toolChangedObservers.forEach(observer -> {
            try {
                observer.onToolRemoved(uniqueName);
            } catch (Exception e) {
                log.error("Failed to notify observer of tool removed. [observer={}, uniqueName={}, error={}]",
                        observer.getClass().getName(),
                        uniqueName,
                        e.getMessage(),
                        e);
            }
        });
    }

    @Override
    public ToolInfoEntity getTool(String namespace, String toolName) {
        notBlank(namespace, "The namespace cannot be blank.");
        notBlank(toolName, "The toll name cannot be blank.");
        String uniqueName = org.fitframework.fel.core.tool.ToolInfo.identify(namespace, toolName);
        return toolCache.get(uniqueName);
    }

    @Override
    public List<ToolInfoEntity> listTool(String namespace) {
        notBlank(namespace, "The namespace cannot be blank.");
        return toolCache.entrySet()
                .stream()
                .filter(entry -> entry.getKey().startsWith(namespace))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}