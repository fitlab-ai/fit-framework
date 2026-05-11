// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.log;

import org.fitframework.util.StringUtils;

/**
 * 表示日志类型的枚举。
 *
 * @author 季聿阶
 * @since 2023-12-24
 */
public enum LoggerType {
    /** 表示 FIT 框架提供的日志系统。 */
    FIT,

    /** 表示 Log4j2 提供的日志系统。 */
    LOG4J2;

    /**
     * 默认的日志系统为 {@link #FIT}。
     */
    public static final LoggerType DEFAULT = FIT;

    /**
     * 将指定日志系统类型转化为 {@link LoggerType}。
     *
     * @param type 表示日志系统类型的 {@link String}。
     * @return 表示转化后的日志系统的 {@link LoggerType}。
     */
    public static LoggerType from(String type) {
        for (LoggerType loggerType : LoggerType.values()) {
            if (StringUtils.equalsIgnoreCase(type, loggerType.name())) {
                return loggerType;
            }
        }
        return DEFAULT;
    }
}
