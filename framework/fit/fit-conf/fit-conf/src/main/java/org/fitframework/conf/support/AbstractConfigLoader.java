// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.conf.support;

import org.fitframework.conf.ConfigLoader;
import org.fitframework.resource.Resource;
import org.fitframework.util.FileUtils;
import org.fitframework.util.StringUtils;

/**
 * 为 {@link ConfigLoader} 提供基类。
 *
 * @author 梁济时
 * @since 2022-12-19
 */
public abstract class AbstractConfigLoader implements ConfigLoader {
    /**
     * 获取配置的名称。
     * <p>若名称是空白字符串，则从 {@link Resource} 的信息来计算资源名称。</p>
     *
     * @param resource 表示配置的资源的 {@link Resource}。
     * @param name 表示配置的名称的 {@link String}。
     * @return 表示配置的名称的 {@link String}。
     */
    protected String nameOfConfig(Resource resource, String name) {
        String actual = StringUtils.trim(name);
        if (StringUtils.isEmpty(actual)) {
            actual = FileUtils.ignoreExtension(resource.filename());
        }
        return actual;
    }
}
