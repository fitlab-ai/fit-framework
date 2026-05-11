// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.lifecycle.container;

import org.fitframework.ioc.BeanContainer;
import org.fitframework.ioc.BeanFactory;
import org.fitframework.ioc.BeanFactoryOrderComparator;

/**
 * 为 Bean 容器停止后提供观察者。
 *
 * @author 季聿阶
 * @since 2023-08-07
 */
@FunctionalInterface
public interface BeanContainerStoppedObserver {
    /**
     * 当 Bean 容器被停止后调用的方法。
     *
     * @param container 表示已停止的 Bean 容器的 {@link BeanContainer}。
     */
    void onBeanContainerStopped(BeanContainer container);

    /**
     * 通知所有容器中所有实现了 {@link BeanContainerStoppedObserver} 接口的 Bean。
     *
     * @param container 表示已停止完成的 Bean 容器的 {@link BeanContainer}。
     */
    static void notify(BeanContainer container) {
        if (container == null) {
            return;
        }
        container.all(BeanContainerStoppedObserver.class)
                .stream()
                .sorted(BeanFactoryOrderComparator.INSTANCE)
                .map(BeanFactory::<BeanContainerStoppedObserver>get)
                .forEach(observer -> observer.onBeanContainerStopped(container));
    }
}
