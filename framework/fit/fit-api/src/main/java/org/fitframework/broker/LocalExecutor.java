// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker;

import org.fitframework.ioc.BeanMetadata;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * 表示本地执行器。
 *
 * @author 季聿阶
 * @since 2023-03-26
 */
public interface LocalExecutor {
    /**
     * 获取本地执行器对应的服务实现的唯一标识。
     *
     * @return 表示本地执行器对应的服务实现的唯一标识的 {@link UniqueFitableId}。
     */
    UniqueFitableId id();

    /**
     * 获取本地执行器的别名集合。
     *
     * @return 表示本地执行器的别名集合的 {@link Set}{@code <}{@link String}{@code >}。
     */
    Set<String> aliases();

    /**
     * 获取本地执行器所在 Bean 的元数据。
     *
     * @return 表示本地执行器所在 Bean 的元数据的 {@link BeanMetadata}。
     */
    BeanMetadata metadata();

    /**
     * 获取本地执行器是否为微服务的标志。
     *
     * @return 表示本地执行器是否为微服务的标志的 {@code boolean}。
     */
    boolean isMicro();

    /**
     * 获取本地执行器对应的方法。
     *
     * @return 表示本地执行器对应的方法的 {@link Method}。
     */
    Method method();

    /**
     * 执行本地执行器。
     *
     * @param args 表示待执行的参数列表的 {@link Object}{@code []}。
     * @return 表示执行结果的 {@link Object}。
     */
    Object execute(Object[] args);
}
