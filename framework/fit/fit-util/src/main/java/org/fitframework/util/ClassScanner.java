// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.util;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 为应用程序提供类扫描工具。
 *
 * @author 梁济时
 * @author 季聿阶
 * @since 2020-09-17
 */
public interface ClassScanner {
    /**
     * 获取扫描的类型所属的类加载器。
     *
     * @return 表示类加载器的 {@link ClassLoader}。
     */
    ClassLoader getClassLoader();

    /**
     * 添加一个观察者，在发现类型时被通知。
     *
     * @param observer 表示观察类型被发现状态变化的观察者的 {@link Consumer}。
     */
    void addClassDetectedObserver(Consumer<String> observer);

    /**
     * 添加一个过滤器，用以根据类型的名称进行过滤。
     *
     * @param classNameFilter 表示类型名称过滤器的 {@link Predicate}。
     */
    void addClassNameFilter(Predicate<String> classNameFilter);

    /**
     * 开始扫描。
     */
    void scan();
}
