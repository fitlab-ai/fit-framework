// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.server.support;

import org.fitframework.broker.server.GenericableServerFilter;
import org.fitframework.broker.server.GenericableServerFilterManager;
import org.fitframework.ioc.BeanFactory;
import org.fitframework.plugin.Plugin;
import org.fitframework.plugin.PluginStartedObserver;
import org.fitframework.plugin.PluginStoppingObserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 表示 {@link GenericableServerFilterManager} 的默认实现。
 *
 * @author 李金绪
 * @since 2024-08-27
 */
public class DefaultGenericableServerFilterManager
        implements GenericableServerFilterManager, PluginStartedObserver, PluginStoppingObserver {
    List<GenericableServerFilter> filters = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void register(GenericableServerFilter filter) {
        if (filter != null) {
            this.filters.add(filter);
            Collections.sort(this.filters, GenericableServerFilter.PriorityComparator.INSTANCE);
        }
    }

    @Override
    public void unregister(GenericableServerFilter filter) {
        if (filter != null) {
            this.filters.remove(filter);
        }
    }

    @Override
    public List<GenericableServerFilter> get() {
        return this.filters;
    }

    @Override
    public void onPluginStarted(Plugin plugin) {
        this.getFilters(plugin).forEach(this::register);
    }

    @Override
    public void onPluginStopping(Plugin plugin) {
        this.getFilters(plugin).forEach(this::unregister);
    }

    private List<GenericableServerFilter> getFilters(Plugin plugin) {
        return plugin.container()
                .factories(GenericableServerFilter.class)
                .stream()
                .map(BeanFactory::<GenericableServerFilter>get)
                .collect(Collectors.toList());
    }
}
