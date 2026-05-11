// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.plugin;

/**
 * 为插件所使用的类加载程序提供工厂。
 *
 * @author 梁济时
 * @since 2022-06-29
 */
@FunctionalInterface
public interface PluginClassLoaderFactory {
    /**
     * 为指定的插件创建类加载程序。
     *
     * @param metadata 表示待创建类加载程序的插件的元数据的 {@link PluginMetadata}。
     * @return 表示插件的类加载程序的 {@link ClassLoader}。
     */
    ClassLoader create(PluginMetadata metadata);
}
