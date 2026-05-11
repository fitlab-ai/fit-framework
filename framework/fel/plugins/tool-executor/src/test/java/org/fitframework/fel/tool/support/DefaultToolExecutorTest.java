// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.tool.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.fitframework.fel.tool.ToolInfoEntity;
import org.fitframework.fel.tool.Tool;
import org.fitframework.fel.tool.info.entity.ToolEntity;
import org.fitframework.fel.tool.ToolFactory;
import org.fitframework.fel.tool.ToolFactoryRepository;
import org.fitframework.fel.tool.ToolSchema;
import org.fitframework.fel.tool.service.ToolExecuteService;
import org.fitframework.fel.tool.service.ToolRepository;
import org.fitframework.serialization.json.jackson.JacksonObjectSerializer;
import org.fitframework.serialization.ObjectSerializer;
import org.fitframework.util.IoUtils;
import org.fitframework.util.StringUtils;
import org.fitframework.util.TypeUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 表示 {@link DefaultToolExecutor} 的测试集。
 *
 * @author 王攀博
 * @author 易文渊
 * @author 杭潇
 * @since 2024-04-27
 */
@DisplayName("测试 DefaultToolExecutor")
public class DefaultToolExecutorTest {
    private ToolRepository toolRepository;
    private ToolFactoryRepository toolFactoryRepository;
    private ToolFactory toolFactory;
    private final ObjectSerializer serializer = new JacksonObjectSerializer(null, null, null, true);

    @BeforeEach
    void setUp() {
        this.toolRepository = mock(ToolRepository.class);
        this.toolFactoryRepository = mock(ToolFactoryRepository.class);
        this.toolFactory = mock(ToolFactory.class);
    }

    @AfterEach
    void tearDown() {
        clearInvocations(this.toolRepository, this.toolFactoryRepository, this.toolFactory);
    }

    @Test
    @DisplayName("调用工具成功返回结果")
    void shouldOkWhenExecuteTool() throws IOException {
        ToolInfoEntity toolEntity = getTestEntity();
        when(this.toolRepository.getTool(any(), eq(toolEntity.name()))).thenReturn(toolEntity);
        when(this.toolFactoryRepository.match(any())).thenReturn(Optional.of(this.toolFactory));
        Tool tool = mock(Tool.class, RETURNS_DEEP_STUBS);
        when(this.toolFactory.create(any(), any())).thenReturn(tool);
        when(tool.executeWithJson(any())).thenReturn("hello");
        when(tool.metadata().returnConverter()).thenReturn(StringUtils.EMPTY);
        ToolExecuteService toolExecutor =
                new DefaultToolExecutor(this.toolRepository, this.toolFactoryRepository, this.serializer);
        assertThat(toolExecutor.execute("test", toolEntity.name(), "test")).isEqualTo("\"hello\"");
    }

    @Test
    @DisplayName("工具不存在，调用失败")
    void shouldFailWhenToolNotFound() {
        ToolExecuteService toolExecutor =
                new DefaultToolExecutor(this.toolRepository, this.toolFactoryRepository, this.serializer);
        when(this.toolRepository.getTool(any(), any())).thenReturn(null);
        assertThatThrownBy(() -> toolExecutor.execute("test",
                "test",
                "test")).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("工具工厂不存在，调用失败")
    void shouldFailWhenToolFactoryNotFound() throws IOException {
        ToolInfoEntity toolEntity = getTestEntity();
        when(this.toolRepository.getTool(any(), any())).thenReturn(toolEntity);
        when(this.toolFactoryRepository.match(any())).thenReturn(Optional.empty());

        ToolExecuteService toolExecutor =
                new DefaultToolExecutor(this.toolRepository, this.toolFactoryRepository, this.serializer);
        assertThatThrownBy(() -> toolExecutor.execute("test",
                "test",
                "test")).isInstanceOf(IllegalStateException.class);
    }

    private ToolInfoEntity getTestEntity() throws IOException {
        List<ToolEntity> toolEntities =
                this.serializer.<Map<String, List<ToolEntity>>>deserialize(IoUtils.content(this.getClass()
                        .getClassLoader(), ToolSchema.TOOL_MANIFEST), TypeUtils.parameterized(Map.class, new Type[] {
                        String.class, TypeUtils.parameterized(List.class, new Type[] {ToolEntity.class})
                })).get("tools");
        return new ToolInfoEntity(toolEntities.get(0));
    }
}