// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc;

/**
 * 为 {@link BeanContainer} 提供构建程序。
 *
 * @author 梁济时
 * @since 2022-07-04
 */
public interface BeanContainerBuilder {
    /**
     * 设置Bean容器的名称。
     *
     * @param name 表示Bean容器名称的 {@link String}。
     * @return 表示当前构建程序的 {@link BeanContainerBuilder}。
     */
    BeanContainerBuilder name(String name);

    /**
     * 获取解析程序的配置程序。
     *
     * @return 表示解析程序配置程序的 {@link BeanContainerBuilderResolverConfigurator}。
     */
    BeanContainerBuilderResolverConfigurator resolvers();

    /**
     * 构建Bean容器实例。
     *
     * @return 表示新构建的Bean容器实例的 {@link BeanContainer}。
     */
    BeanContainer build();
}
