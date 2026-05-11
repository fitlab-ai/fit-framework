// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.plugin.support;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * 为插件提供类加载器。
 *
 * @author 梁济时
 * @author 季聿阶
 * @since 2022-06-29
 */
public class PluginClassLoader extends URLClassLoader {
    static {
        ClassLoader.registerAsParallelCapable();
    }

    /**
     * 向插件类加载器中添加一个 URL。
     *
     * @param parent 表示父类加载器的 {@link ClassLoader}。
     */
    public PluginClassLoader(ClassLoader parent) {
        super(new URL[0], parent);
    }

    /**
     * 向插件类加载器中添加一个 URL。
     *
     * @param url 表示待添加的 {@link URL}。
     */
    public void add(URL url) {
        this.addURL(url);
    }
}

