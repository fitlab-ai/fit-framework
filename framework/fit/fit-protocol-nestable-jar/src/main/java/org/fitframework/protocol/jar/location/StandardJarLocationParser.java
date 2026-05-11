// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.protocol.jar.location;

import org.fitframework.protocol.jar.JarLocationParser;

/**
 * 表示 {@link JarLocationParser} 的标准解析器。
 *
 * @author 高三海
 * @author 季聿阶
 * @since 2025-02-18
 */
class StandardJarLocationParser extends AbstractJarLocationParser {
    static final JarLocationParser INSTANCE = new StandardJarLocationParser();

    private static final String SEPARATOR = "!/";
    private static final String SUPPORTED_PROTOCOL_PREFIX = "file:";

    private StandardJarLocationParser() {}

    @Override
    protected boolean isSupported(String url, int start, int stop) {
        return url.regionMatches(true, start, SUPPORTED_PROTOCOL_PREFIX, 0, SUPPORTED_PROTOCOL_PREFIX.length());
    }

    @Override
    protected String getSeparator() {
        return SEPARATOR;
    }

    @Override
    protected String process(String toProcess) {
        return toProcess;
    }
}
