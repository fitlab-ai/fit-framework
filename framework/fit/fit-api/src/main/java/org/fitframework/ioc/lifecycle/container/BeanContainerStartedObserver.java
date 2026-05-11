// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.lifecycle.container;

import org.fitframework.ioc.BeanContainer;
import org.fitframework.ioc.BeanFactory;
import org.fitframework.ioc.BeanFactoryOrderComparator;

/**
 * 为 Bean 容器启动后提供观察者。
 *
 * @author 梁济时
 * @since 2022-06-08
 */
@FunctionalInterface
public interface BeanContainerStartedObserver {
    /**
     * 当 Bean 容器被启动后调用的方法。
     *
     * @param container 表示已启动的 Bean 容器的 {@link BeanContainer}。
     */
    void onBeanContainerStarted(BeanContainer container);

    /**
     * 通知所有容器中所有实现了 {@link BeanContainerStartedObserver} 接口的 Bean。
     *
     * @param container 表示已启动完成的 Bean 容器的 {@link BeanContainer}。
     */
    static void notify(BeanContainer container) {
        if (container == null) {
            return;
        }
        container.all(BeanContainerStartedObserver.class)
                .stream()
                .sorted(BeanFactoryOrderComparator.INSTANCE)
                .map(BeanFactory::<BeanContainerStartedObserver>get)
                .forEach(observer -> observer.onBeanContainerStarted(container));
    }
}
