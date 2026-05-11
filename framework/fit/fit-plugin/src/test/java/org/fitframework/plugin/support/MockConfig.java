// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.plugin.support;

import org.fitframework.conf.Config;
import org.fitframework.conf.ConfigDecryptor;
import org.fitframework.conf.ConfigValueSupplier;
import org.fitframework.conf.support.MapConfig;
import org.fitframework.inspection.Nonnull;
import org.fitframework.util.MapBuilder;

import java.lang.reflect.Type;
import java.util.Set;

/**
 * 表示模拟的配置类。
 *
 * @author 季聿阶
 * @since 2023-10-10
 */
public class MockConfig implements Config, ConfigValueSupplier {
    private final Config config;

    MockConfig() {
        this.config = new MapConfig("Mock",
                MapBuilder.<String, Object>get().put("prefix.k1", "v1").put("prefix.k2", "v2").put("k3", "v3").build());
    }

    @Override
    public String name() {
        return this.config.name();
    }

    @Override
    public Set<String> keys() {
        return this.config.keys();
    }

    @Override
    public Object get(String key, Type type) {
        return this.config.get(key, type);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return this.config.get(key, clazz);
    }

    @Override
    public void decrypt(@Nonnull ConfigDecryptor decryptor) {
        this.config.decrypt(decryptor);
    }

    @Override
    public Object get(String key) {
        return ConfigValueSupplier.get(this.config, key);
    }
}
