// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.security.http.name.support;

import static org.fitframework.util.ObjectUtils.nullIf;

import org.fitframework.security.http.name.ConfigurableFileNameValidateConfig;
import org.fitframework.security.http.zipped.ConfigurableZippedFileValidateConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 表示 {@link ConfigurableZippedFileValidateConfig} 的默认实现。
 * <p>配置项不能够设置为空，如果设置为空则自动设定为默认初始值。</p>
 *
 * @author 何天放
 * @since 2024-07-18
 */
public final class DefaultConfigurableFileNameValidateConfig implements ConfigurableFileNameValidateConfig {
    /**
     * 表示 {@link ConfigurableZippedFileValidateConfig} 默认实现的实例，通过该实例作为配置对于文件进行校验时将不会进行任何实质性校验。
     */
    public static final ConfigurableFileNameValidateConfig INSTANCE = new DefaultConfigurableFileNameValidateConfig();

    private static final String DEFAULT_FILE_NAME_FORMAT = "[\\s\\S]*";
    private static final List<String> DEFAULT_BLACK_LIST = new ArrayList<>();
    private static final List<String> DEFAULT_EXTENSION_NAME_WHITE_LIST = Collections.emptyList();

    private String fileNameFormat = DEFAULT_FILE_NAME_FORMAT;
    private List<String> blackList = DEFAULT_BLACK_LIST;
    private List<String> extensionNameWhiteList = DEFAULT_EXTENSION_NAME_WHITE_LIST;

    @Override
    public ConfigurableFileNameValidateConfig fileNameFormat(String fileNameFormat) {
        this.fileNameFormat = nullIf(fileNameFormat, DEFAULT_FILE_NAME_FORMAT);
        return this;
    }

    @Override
    public ConfigurableFileNameValidateConfig blackList(List<String> blackList) {
        this.blackList = nullIf(blackList, DEFAULT_BLACK_LIST);
        return this;
    }

    @Override
    public ConfigurableFileNameValidateConfig extensionNameWhiteList(List<String> extensionNameWhiteList) {
        this.extensionNameWhiteList = nullIf(extensionNameWhiteList, DEFAULT_EXTENSION_NAME_WHITE_LIST);
        return this;
    }

    @Override
    public String fileNameFormat() {
        return this.fileNameFormat;
    }

    @Override
    public List<String> blackList() {
        return this.blackList;
    }

    @Override
    public List<String> extensionNameWhiteList() {
        return this.extensionNameWhiteList;
    }
}
