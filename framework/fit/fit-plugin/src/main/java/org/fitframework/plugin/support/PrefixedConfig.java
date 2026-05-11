// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.plugin.support;

import org.fitframework.conf.Config;
import org.fitframework.conf.ConfigDecryptor;
import org.fitframework.conf.ConfigValueSupplier;
import org.fitframework.conf.support.AbstractConfig;
import org.fitframework.inspection.Nonnull;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 为 {@link Config} 提供增加前缀的装饰程序。
 *
 * @author 梁济时
 * @since 2023-01-05
 */
final class PrefixedConfig extends AbstractConfig {
    private final Config decorated;
    private final String prefix;

    PrefixedConfig(String name, Config decorated, String prefix) {
        super(name);
        this.decorated = decorated;
        this.prefix = Config.canonicalizeKey(prefix) + ".";
    }

    @Override
    public Set<String> keys() {
        return this.decorated.keys()
                .stream()
                .filter(key -> key.startsWith(this.prefix))
                .map(key -> key.substring(this.prefix.length()))
                .collect(Collectors.toSet());
    }

    @Override
    public Object getWithCanonicalKey(String key) {
        String full = this.prefix + key;
        return ConfigValueSupplier.get(this.decorated, full);
    }

    @Override
    public void decrypt(@Nonnull ConfigDecryptor decryptor) {
        this.decorated.decrypt(decryptor);
    }
}
