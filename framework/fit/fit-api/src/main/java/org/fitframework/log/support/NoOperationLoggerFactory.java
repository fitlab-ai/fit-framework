// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.log.support;

import org.fitframework.conf.Config;
import org.fitframework.log.Logger;
import org.fitframework.log.LoggerFactory;

/**
 * 表示 {@link LoggerFactory} 的忽略日志的实现。
 *
 * @author 季聿阶
 * @since 2023-06-15
 */
public class NoOperationLoggerFactory implements LoggerFactory {
    /** 表示 {@link NoOperationLoggerFactory} 的单例。 */
    public static final NoOperationLoggerFactory INSTANCE = new NoOperationLoggerFactory();

    private NoOperationLoggerFactory() {}

    @Override
    public void initialize(Config config, ClassLoader frameworkClassLoader) {}

    @Override
    public Logger getLogger(Class<?> clazz) {
        return NoOperationLogger.INSTANCE;
    }

    @Override
    public Logger getLogger(String name) {
        return NoOperationLogger.INSTANCE;
    }

    @Override
    public void setGlobalLevel(Logger.Level level) {}

    @Override
    public void setLevels(String basePackage, Logger.Level level) {}
}
