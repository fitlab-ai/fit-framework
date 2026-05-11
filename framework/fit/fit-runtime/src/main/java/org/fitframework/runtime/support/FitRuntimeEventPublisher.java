// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.runtime.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.event.Event;
import org.fitframework.event.EventPublisher;
import org.fitframework.plugin.Plugin;
import org.fitframework.runtime.FitRuntime;

import java.util.List;

/**
 * 为 {@link FitRuntime} 提供事件发布程序。
 *
 * @author 梁济时
 * @since 2023-01-31
 */
final class FitRuntimeEventPublisher implements EventPublisher {
    private final FitRuntime runtime;

    FitRuntimeEventPublisher(FitRuntime runtime) {
        this.runtime = notNull(runtime, "The FIT runtime of event publisher cannot be null.");
    }

    @Override
    public <E extends Event> void publishEvent(E event) {
        List<Plugin> plugins = this.runtime.plugins();
        RuntimeException exception = null;
        for (Plugin plugin : plugins) {
            try {
                plugin.publisherOfEvents().publishEvent(event);
            } catch (RuntimeException ex) {
                if (exception == null) {
                    exception = ex;
                } else {
                    exception.addSuppressed(ex);
                }
            }
        }
        if (exception != null) {
            throw exception;
        }
    }
}
