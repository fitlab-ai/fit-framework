// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.tool.support;

import static org.fitframework.inspection.Validation.isTrue;
import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.fel.tool.ToolInfoEntity;
import org.fitframework.fel.tool.ToolSchema;
import org.fitframework.fel.tool.info.entity.ToolEntity;
import org.fitframework.fel.tool.info.entity.ToolGroupEntity;
import org.fitframework.fel.tool.info.entity.ToolJsonEntity;
import org.fitframework.fel.tool.service.ToolRepository;
import org.fitframework.annotation.Component;
import org.fitframework.annotation.Fit;
import org.fitframework.annotation.Value;
import org.fitframework.plugin.Plugin;
import org.fitframework.plugin.PluginStartedObserver;
import org.fitframework.plugin.PluginStoppingObserver;
import org.fitframework.resource.Resource;
import org.fitframework.serialization.ObjectSerializer;
import org.fitframework.util.ArrayUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 表示默认的工具方法自动装配器。
 * <p>当插件加载和卸载时，会根据插件暴露的工具方法，进行工具的加载和卸载。</p>
 *
 * @author 易文渊
 * @author 杭潇
 * @since 2024-08-15
 */
@Component
public class DefaultToolDiscoverer implements PluginStartedObserver, PluginStoppingObserver {
    private final ToolRepository toolRepository;
    private final ObjectSerializer serializer;
    private final int maxToolNum;

    /**
     * 构造工具自动装配器的实例。
     *
     * @param toolRepository 表示工具仓库的 {@link ToolRepository}。
     * @param serializer 表示对象序列化器的 {@link ObjectSerializer}。
     * @param maxNum 表示插件最大工具数量的 {@code int}。
     * @throws IllegalArgumentException 当 {@code toolRepository}、{@code objectSerializer} 为 {@code null} 时。
     */
    public DefaultToolDiscoverer(ToolRepository toolRepository, @Fit(alias = "json") ObjectSerializer serializer,
            @Value("${tool.max-num}") int maxNum) {
        this.toolRepository = notNull(toolRepository, "The tool repository cannot be null.");
        this.serializer = notNull(serializer, "The serializer cannot be null.");
        this.maxToolNum = maxNum;
    }

    @Override
    public void onPluginStarted(Plugin plugin) {
        this.scanTools(plugin).forEach(toolEntity -> this.toolRepository.addTool(new ToolInfoEntity(toolEntity)));
    }

    @Override
    public void onPluginStopping(Plugin plugin) {
        List<ToolEntity> toolEntities = this.scanTools(plugin);
        isTrue(toolEntities.size() < this.maxToolNum, "The tool num in plugin must less than {}", this.maxToolNum);
        toolEntities.forEach(tool -> this.toolRepository.deleteTool(tool.getNamespace(), tool.getSchema().getName()));
    }

    private List<ToolEntity> scanTools(Plugin plugin) {
        try {
            Resource[] resources = plugin.resolverOfResources().resolve(ToolSchema.TOOL_MANIFEST);
            if (ArrayUtils.isEmpty(resources)) {
                return Collections.emptyList();
            }
            return Arrays.stream(resources)
                    .flatMap(resource -> this.parseTools(resource).stream())
                    .collect(Collectors.toList());
        } catch (IOException exception) {
            return Collections.emptyList();
        }
    }

    private List<ToolEntity> parseTools(Resource resource) {
        try (InputStream in = resource.read()) {
            ToolJsonEntity toolJsonEntity = this.serializer.deserialize(in, ToolJsonEntity.class);
            if (toolJsonEntity == null) {
                return Collections.emptyList();
            }
            return toolJsonEntity.getToolGroups()
                    .stream()
                    .filter(Objects::nonNull)
                    .map(ToolGroupEntity::getTools)
                    .filter(Objects::nonNull)
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        } catch (IOException exception) {
            return Collections.emptyList();
        }
    }
}