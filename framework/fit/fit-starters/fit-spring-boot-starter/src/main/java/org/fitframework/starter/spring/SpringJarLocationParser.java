// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.starter.spring;

import org.fitframework.protocol.jar.JarLocationParser;
import org.fitframework.protocol.jar.location.AbstractJarLocationParser;

/**
 * 表示 {@link JarLocationParser} 的 SpringBoot 的实现。
 *
 * @author 高三海
 * @author 季聿阶
 * @since 2025-02-18
 */
public class SpringJarLocationParser extends AbstractJarLocationParser {
    static final JarLocationParser INSTANCE = new SpringJarLocationParser();

    private static final String SUPPORTED_PROTOCOL_NESTED = "nested";
    private static final String PROTOCOL_FILE = "file";
    private static final String SEPARATOR = "/!";

    private SpringJarLocationParser() {}

    @Override
    protected boolean isSupported(String url, int start, int stop) {
        return url.regionMatches(true,
                start,
                SUPPORTED_PROTOCOL_NESTED + ":",
                0,
                SUPPORTED_PROTOCOL_NESTED.length() + 1);
    }

    @Override
    protected String getSeparator() {
        return SEPARATOR;
    }

    @Override
    protected String process(String toProcess) {
        return PROTOCOL_FILE + toProcess.substring(SUPPORTED_PROTOCOL_NESTED.length());
    }
}
