// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.conf.support;

import org.fitframework.conf.Config;
import org.fitframework.conf.ConfigDecryptor;
import org.fitframework.inspection.Nonnull;
import org.fitframework.log.Logger;

import java.util.Properties;
import java.util.Set;

/**
 * 为 {@link Config} 提供基于属性集存储配置值的实现。
 *
 * @author 梁济时
 * @since 2022-12-14
 */
public class ReadonlyPropertiesConfig extends AbstractConfig {
    private static final Logger log = Logger.get(ReadonlyPropertiesConfig.class);

    private final PropertiesConfig properties;

    /**
     * 使用配置的名称及包含配置值的属性集初始化 {@link ReadonlyPropertiesConfig} 类的新实例。
     *
     * @param name 表示配置的名称的 {@link String}。
     * @param properties 表示包含配置值的属性集的 {@link Properties}。
     */
    public ReadonlyPropertiesConfig(String name, Properties properties) {
        super(name);
        this.properties = new PropertiesConfig(name, properties);
    }

    @Override
    public Set<String> keys() {
        return this.properties.keys();
    }

    @Override
    public Object getWithCanonicalKey(String key) {
        return this.properties.get(key);
    }

    @Override
    public void decrypt(@Nonnull ConfigDecryptor decryptor) {
        log.debug("The readonly properties config does not support decrypting.");
    }
}
