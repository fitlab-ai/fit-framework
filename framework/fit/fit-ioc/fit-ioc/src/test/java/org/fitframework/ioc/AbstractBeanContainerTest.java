// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.fitframework.inspection.Nonnull;
import org.fitframework.ioc.annotation.support.DefaultAnnotationMetadataResolver;
import org.fitframework.ioc.support.DefaultBeanContainer;
import org.fitframework.ioc.support.DefaultBeanResolver;
import org.fitframework.ioc.support.DefaultDependencyResolver;
import org.fitframework.plugin.Plugin;
import org.fitframework.plugin.PluginCollection;
import org.fitframework.runtime.FitRuntime;

import java.net.URL;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * 为Bean容器的测试提供基类。
 *
 * @author 梁济时
 * @since 2022-06-27
 */
public abstract class AbstractBeanContainerTest {
    protected static final PluginCollection EMPTY_PLUGIN_COLLECTION;

    static {
        EMPTY_PLUGIN_COLLECTION = emptyPluginCollection();
    }

    protected static BeanContainer container() {
        FitRuntime runtime = mock(FitRuntime.class);
        when(runtime.resolverOfBeans()).thenReturn(new DefaultBeanResolver());
        when(runtime.resolverOfDependencies()).thenReturn(new DefaultDependencyResolver());
        when(runtime.resolverOfAnnotations()).thenReturn(new DefaultAnnotationMetadataResolver());
        Plugin plugin = mock(Plugin.class);
        when(plugin.runtime()).thenReturn(runtime);
        return container(plugin);
    }

    protected static BeanContainer container(Plugin plugin) {
        BeanContainer container = new DefaultBeanContainer(plugin);
        when(plugin.container()).thenReturn(container);
        PluginCollection emptyPluginCollection = emptyPluginCollection();
        when(plugin.children()).thenReturn(emptyPluginCollection);
        return container;
    }

    private static PluginCollection emptyPluginCollection() {
        PluginCollection plugins = mock(PluginCollection.class);
        when(plugins.size()).thenReturn(0);
        when(plugins.add(any())).thenReturn(null);
        when(plugins.remove(any())).thenReturn(null);
        when(plugins.get(anyInt())).thenReturn(null);
        when(plugins.get(any(URL.class))).thenReturn(null);
        when(plugins.contains(any())).thenReturn(false);
        when(plugins.iterator()).thenReturn(Collections.emptyIterator());
        return plugins;
    }

    protected static PluginCollection plugins(List<Plugin> plugins) {
        return new PluginCollection() {
            @Override
            public int size() {
                return plugins.size();
            }

            @Override
            public Plugin add(URL location) {
                throw new IllegalStateException("Cannot create plugin.");
            }

            @Override
            public Plugin remove(URL location) {
                throw new IllegalStateException("Cannot remove plugin.");
            }

            @Override
            public Plugin get(int index) {
                return plugins.get(index);
            }

            @Override
            public Plugin get(URL location) {
                return plugins.stream()
                        .filter(plugin -> plugin.metadata().location().equals(location))
                        .findAny().orElse(null);
            }

            @Override
            public boolean contains(URL location) {
                return plugins.stream().anyMatch(plugin -> plugin.metadata().location().equals(location));
            }

            @Override
            public Stream<Plugin> stream() {
                return plugins.stream();
            }

            @Nonnull
            @Override
            public Iterator<Plugin> iterator() {
                return plugins.iterator();
            }
        };
    }
}
