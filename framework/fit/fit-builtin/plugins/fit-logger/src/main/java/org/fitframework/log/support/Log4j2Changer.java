// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.log.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.log.LoggerLevelChanger;
import org.fitframework.plugin.Plugin;
import org.fitframework.runtime.FitRuntime;
import org.fitframework.util.ObjectUtils;
import org.fitframework.util.StringUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.apache.logging.log4j.spi.LoggerContext;

/**
 * 表示 {@link LoggerLevelChanger} 的 Log4j2 的实现。
 *
 * @author 季聿阶
 * @since 2023-12-24
 */
public class Log4j2Changer implements LoggerLevelChanger {
    private final FitRuntime runtime;

    public Log4j2Changer(FitRuntime runtime) {
        this.runtime = notNull(runtime, "The FIT runtime cannot be null.");
    }

    @Override
    public void changeLevel(String pluginName, String packageName, String name, String level) {
        org.apache.logging.log4j.Level log4j2Level = org.apache.logging.log4j.Level.getLevel(level);
        Plugin plugin = this.runtime.plugin(pluginName)
                .orElseThrow(() -> new IllegalStateException(StringUtils.format("No plugin. [pluginName={0}]",
                        pluginName)));
        LoggerContext context = LogManager.getContext(plugin.pluginClassLoader(), false);
        if (StringUtils.isNotBlank(name)) {
            ExtendedLogger logger = context.getLogger(name);
            if (logger instanceof Logger) {
                Logger actualLogger = ObjectUtils.cast(logger);
                actualLogger.setLevel(log4j2Level);
            }
        } else {
            Configurator.setAllLevels(packageName, log4j2Level);
        }
    }
}
