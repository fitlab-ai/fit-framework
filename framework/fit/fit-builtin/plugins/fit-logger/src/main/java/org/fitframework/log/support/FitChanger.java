// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.log.support;

import org.fitframework.log.LoggerLevelChanger;
import org.fitframework.log.Logger;
import org.fitframework.log.Loggers;
import org.fitframework.util.StringUtils;

/**
 * 表示 {@link LoggerLevelChanger} 的 FIT 框架的默认实现。
 *
 * @author 季聿阶
 * @since 2023-12-24
 */
public class FitChanger implements LoggerLevelChanger {
    /** 表示 {@link FitChanger} 单例。 */
    public static final FitChanger INSTANCE = new FitChanger();

    @Override
    public void changeLevel(String pluginName, String packageName, String name, String level) {
        Logger.Level loggerLevel = Logger.Level.from(level);
        if (StringUtils.isNotBlank(name)) {
            Loggers.getFactory().getLogger(name).setLevel(loggerLevel);
        } else {
            Loggers.getFactory().setLevels(packageName, loggerLevel);
        }
    }
}
