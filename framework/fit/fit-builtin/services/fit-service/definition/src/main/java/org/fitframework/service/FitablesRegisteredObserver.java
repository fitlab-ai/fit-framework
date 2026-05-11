// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.service;

import org.fitframework.ioc.BeanContainer;
import org.fitframework.ioc.BeanFactory;
import org.fitframework.ioc.BeanFactoryOrderComparator;

/**
 * 表示全部的服务实现都已经注册完毕的事件。
 *
 * @author 季聿阶
 * @since 2022-09-12
 */
@FunctionalInterface
public interface FitablesRegisteredObserver {
    /**
     * 当所有的服务实现都已经注册完毕时，调用的方法。
     */
    void onFitablesRegistered();

    /**
     * 通知所有容器中所有实现了 {@link FitablesRegisteredObserver} 接口的 Bean。
     *
     * @param container 表示已初始化完成的 Bean 容器的 {@link BeanContainer}。
     */
    static void notify(BeanContainer container) {
        if (container == null) {
            return;
        }
        container.all(FitablesRegisteredObserver.class)
                .stream()
                .sorted(BeanFactoryOrderComparator.INSTANCE)
                .map(BeanFactory::<FitablesRegisteredObserver>get)
                .forEach(FitablesRegisteredObserver::onFitablesRegistered);
    }
}
