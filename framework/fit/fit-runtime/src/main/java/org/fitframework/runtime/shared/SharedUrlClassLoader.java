// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.runtime.shared;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * 表示 {@link URLClassLoader} 的共享类加载器的实现。
 *
 * @author 季聿阶
 * @since 2024-09-14
 */
public class SharedUrlClassLoader extends URLClassLoader {
    public SharedUrlClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }
}
