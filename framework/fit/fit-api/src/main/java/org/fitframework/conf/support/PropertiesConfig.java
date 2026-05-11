// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.conf.support;

import org.fitframework.conf.Config;
import org.fitframework.conf.ModifiableConfig;
import org.fitframework.util.ObjectUtils;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 为 {@link ModifiableConfig} 提供基于配置集的实现。
 *
 * @author 梁济时
 * @since 2022-06-17
 */
public class PropertiesConfig extends AbstractModifiableConfig {
    private final Properties properties;

    /**
     * 使用配置的名称和包含配置内容的属性集初始化 {@link PropertiesConfig} 类的新实例。
     *
     * @param name 表示配置的名称的 {@link String}。
     * @param properties 表示包含配置内容的属性集的 {@link Properties}。
     */
    public PropertiesConfig(String name, Properties properties) {
        super(name);
        this.properties = new Properties();
        if (properties != null) {
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                this.properties.put(Config.canonicalizeKey(String.valueOf(entry.getKey())), entry.getValue());
            }
        }
    }

    @Override
    public Set<String> keys() {
        return this.properties.stringPropertyNames();
    }

    @Override
    public Object getWithCanonicalKey(String key) {
        return this.properties.getProperty(key);
    }

    @Override
    public void setWithCanonicalKey(String key, Object value) {
        this.properties.setProperty(key, ObjectUtils.toString(value));
        this.notifyValueChanged(key);
    }
}
