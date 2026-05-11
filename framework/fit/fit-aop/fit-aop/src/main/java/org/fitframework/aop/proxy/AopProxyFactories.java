// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.proxy;

import org.fitframework.annotation.Order;
import org.fitframework.ioc.annotation.AnnotationMetadata;
import org.fitframework.ioc.annotation.AnnotationMetadataResolver;
import org.fitframework.ioc.annotation.AnnotationMetadataResolvers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;

/**
 * 表示 AOP 代理的工厂帮助类。
 *
 * @author 季聿阶
 * @since 2024-10-28
 */
public class AopProxyFactories {
    private final List<AopProxyFactory> orderedFactories;

    /**
     * 初始化 {@link AopProxyFactories} 的新实例。
     * <p>实例化时会自动加载并排序所有可用的 {@link AopProxyFactory}。</p>
     */
    public AopProxyFactories() {
        this.orderedFactories = all();
    }

    /**
     * 获取所有排序好的的 {@link AopProxyFactory}。
     *
     * @return 表示获取到的所有排序好的的 {@link List}{@code <}{@link AopProxyFactory}{@code >}。
     */
    public List<AopProxyFactory> getAll() {
        return this.orderedFactories;
    }

    private static List<AopProxyFactory> all() {
        ServiceLoader<AopProxyFactory> loader =
                ServiceLoader.load(AopProxyFactory.class, AopProxyFactory.class.getClassLoader());
        List<AopProxyFactory> factories = new ArrayList<>();
        loader.forEach(factories::add);
        factories.sort((f1, f2) -> {
            AnnotationMetadataResolver resolver = AnnotationMetadataResolvers.create();
            AnnotationMetadata annotations = resolver.resolve(f1.getClass());
            int order1 =
                    Optional.ofNullable(annotations.getAnnotation(Order.class)).map(Order::value).orElse(Order.MEDIUM);
            annotations = resolver.resolve(f2.getClass());
            int order2 =
                    Optional.ofNullable(annotations.getAnnotation(Order.class)).map(Order::value).orElse(Order.MEDIUM);
            return Integer.compare(order1, order2);
        });
        return factories;
    }
}
