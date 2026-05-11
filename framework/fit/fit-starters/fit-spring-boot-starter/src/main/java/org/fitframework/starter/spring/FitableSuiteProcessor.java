// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.starter.spring;

import org.fitframework.annotation.FitableSuite;
import org.fitframework.broker.LocalExecutorResolver;
import org.fitframework.ioc.BeanFactory;
import org.fitframework.ioc.BeanMetadata;
import org.fitframework.ioc.BeanRegistry;
import org.fitframework.runtime.FitRuntime;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 表示带有 {@link FitableSuite} 注解的 Bean 的后置处理器。
 * <p>将带有 {@link FitableSuite} 注解的 Bean 注册到 FIT 容器中，使得 FIT 动态插件中可以调用 Spring 框架中的方法。</p>
 *
 * @author 季聿阶
 * @since 2024-04-14
 */
@Component
public class FitableSuiteProcessor implements BeanPostProcessor {
    private final BeanRegistry registry;
    private final LocalExecutorResolver.RootContainer resolver;

    /**
     * 通过 FIT 运行时来初始化 {@link FitableSuiteProcessor} 的新实例。
     *
     * @param fitRuntime 表示 FIT 运行时的 {@link FitRuntime}。
     */
    public FitableSuiteProcessor(FitRuntime fitRuntime) {
        this.registry = fitRuntime.root().container().registry();
        this.resolver = fitRuntime.root()
                .container()
                .factory(LocalExecutorResolver.RootContainer.class)
                .map(BeanFactory::<LocalExecutorResolver.RootContainer>get)
                .orElseThrow(() -> new IllegalStateException("No local executor resolver in FIT runtime."));
    }

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (AnnotationUtils.findAnnotation(bean.getClass(), FitableSuite.class) != null) {
            List<BeanMetadata> beanMetadataList = this.registry.register(bean);
            beanMetadataList.forEach(this.resolver::resolveAll);
        }
        return bean;
    }
}
