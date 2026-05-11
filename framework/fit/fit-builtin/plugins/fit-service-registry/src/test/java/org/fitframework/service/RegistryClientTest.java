// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.service;

import static org.fitframework.util.ObjectUtils.cast;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.fitframework.broker.LocalExecutor;
import org.fitframework.broker.LocalExecutorFactory;
import org.fitframework.broker.UniqueFitableId;
import org.fitframework.ioc.BeanContainer;
import org.fitframework.plugin.Plugin;
import org.fitframework.plugin.PluginMetadata;
import org.fitframework.schedule.ThreadPoolScheduler;
import org.fitframework.util.ObjectUtils;
import org.fitframework.util.ReflectionUtils;
import org.fitframework.util.ThreadUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Collections;

/**
 * {@link RegistryClient} 的测试类
 *
 * @author 李鑫
 * @since 2021-12-14
 */
@DisplayName("测试 RegistryClient")
public class RegistryClientTest {
    private RegisterFitableService registerFitableService;
    private LocalExecutorFactory localExecutorFactory;
    private RegistryClient registryClient;

    @BeforeEach
    void setup() {
        BeanContainer container = mock(BeanContainer.class);
        this.registerFitableService = Mockito.mock(RegisterFitableService.class);
        this.localExecutorFactory = mock(LocalExecutorFactory.class);
        this.registryClient =
                new RegistryClient(container, this.registerFitableService, this.localExecutorFactory, 25L);
    }

    @AfterEach
    void teardown() throws NoSuchFieldException, InterruptedException {
        this.registerFitableService = null;
        Field field = RegistryClient.class.getDeclaredField("registerFitablesExecutor");
        ThreadPoolScheduler executorService = cast(ReflectionUtils.getField(this.registryClient, field));
        executorService.shutdown();
        this.registryClient = null;
    }

    @Nested
    @DisplayName("当一个不包含任何服务实现的插件启动完毕之后")
    class After1PluginWithoutFitablesStarted {
        private Plugin startedPlugin;

        @BeforeEach
        void setup() {
            this.startedPlugin = mock(Plugin.class);
            PluginMetadata metadata = mock(PluginMetadata.class);
            when(metadata.name()).thenReturn("demo");
            when(this.startedPlugin.metadata()).thenReturn(metadata);
            when(RegistryClientTest.this.localExecutorFactory.get(any(),
                    anyBoolean())).thenReturn(Collections.emptyList());
            RegistryClientTest.this.registryClient.onPluginStarted(this.startedPlugin);
        }

        @AfterEach
        void teardown() {
            this.startedPlugin = null;
        }

        @Test
        @DisplayName("当运行时启动完毕后，没有服务进行了注册")
        void noFitablesRegisteredAfterRuntimeStarted() {
            RegistryClientTest.this.registryClient.onRuntimeStarted(null);
            verify(RegistryClientTest.this.registerFitableService, times(0)).register(any());
        }
    }

    @Nested
    @DisplayName("当一个插件启动完毕之后")
    class After1PluginStarted {
        private Plugin startedPlugin;

        @BeforeEach
        void setup() {
            this.startedPlugin = mock(Plugin.class);
            LocalExecutor localExecutor = mock(LocalExecutor.class);
            UniqueFitableId id = UniqueFitableId.create("gId", "1.0.0", "fId", "1.0.0");
            when(localExecutor.id()).thenReturn(id);
            PluginMetadata metadata = mock(PluginMetadata.class);
            when(metadata.name()).thenReturn("demo");
            when(this.startedPlugin.metadata()).thenReturn(metadata);
            when(RegistryClientTest.this.localExecutorFactory.get(any(),
                    anyBoolean())).thenReturn(Collections.singletonList(localExecutor));
            RegistryClientTest.this.registryClient.onPluginStarted(this.startedPlugin);
        }

        @AfterEach
        void teardown() {
            this.startedPlugin = null;
        }

        @Test
        @DisplayName("当运行时启动完毕后，服务进行了 1 次注册")
        void fitablesRegistered1TimeAfterRuntimeStarted() {
            RegistryClientTest.this.registryClient.onRuntimeStarted(null);
            while (!this.getRegisteredFlag()) {
                ThreadUtils.sleep(0);
            }
            verify(RegistryClientTest.this.registerFitableService, times(1)).register(any());
        }

        private boolean getRegisteredFlag() {
            return ObjectUtils.cast(ReflectionUtils.getField(RegistryClientTest.this.registryClient,
                    "isFitablesAlreadyRegistered"));
        }
    }
}
