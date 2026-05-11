// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.support;

import org.fitframework.ioc.BeanContainer;
import org.fitframework.ioc.BeanDefinition;
import org.fitframework.ioc.BeanDependency;
import org.fitframework.ioc.BeanMetadata;
import org.fitframework.ioc.DependencyResolvingResult;
import org.fitframework.ioc.annotation.AnnotationMetadata;
import org.fitframework.ioc.annotation.PostConstruct;
import org.fitframework.ioc.annotation.PreDestroy;
import org.fitframework.ioc.annotation.Resource;
import org.fitframework.ioc.lifecycle.bean.ValueSupplier;
import org.fitframework.util.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * 为 {@link org.fitframework.ioc.BeanResolver BeanResolver} 提供基于 <a
 * href="https://jcp.org/en/jsr/detail?id=250">JSR-250</a> 的实现。
 * <p>该实现仅提供 {@link Resource}、{@link PostConstruct}、{@link PreDestroy} 的实现。</p>
 *
 * @author 梁济时
 * @author 季聿阶
 * @since 2022-06-13
 */
public class PartialJsr250BeanResolver extends AbstractAnnotatedBeanResolver {
    @Override
    public Optional<BeanDefinition> bean(BeanContainer container, Class<?> clazz) {
        return Optional.empty();
    }

    @Override
    public Optional<BeanDefinition> bean(BeanContainer container, Method method) {
        return Optional.empty();
    }

    @Override
    public boolean preferred(BeanMetadata metadata, Constructor<?> constructor) {
        return false;
    }

    @Override
    public Optional<ValueSupplier> parameter(BeanMetadata metadata, Parameter parameter) {
        AnnotationMetadata annotations = metadata.runtime().resolverOfAnnotations().resolve(parameter);
        Resource annotation = annotations.getAnnotation(Resource.class);
        if (annotation == null) {
            return Optional.empty();
        } else {
            String name = StringUtils.trim(annotation.name());
            return Optional.of(BeanDependency.create(metadata,
                            name,
                            parameter.getParameterizedType(),
                            false,
                            annotations))
                    .map(BeanDependency::resolve)
                    .filter(DependencyResolvingResult::resolved)
                    .map(result -> result::get);
        }
    }

    @Override
    public Optional<Integer> priority(BeanMetadata metadata) {
        return Optional.empty();
    }

    @Override
    public Set<String> packages(BeanMetadata metadata) {
        return Collections.emptySet();
    }

    @Override
    public Set<String> configurations(BeanMetadata metadata) {
        return Collections.emptySet();
    }

    @Override
    public Optional<Factory> factory(BeanMetadata metadata) {
        return Optional.empty();
    }

    @Override
    protected Optional<BeanDependency> dependency(BeanMetadata metadata, AnnotationMetadata annotations, Type type) {
        return Optional.ofNullable(annotations.getAnnotation(Resource.class))
                .map(resource -> BeanDependency.create(metadata, resource.name(), type, false, annotations));
    }

    @Override
    protected boolean isInjector(BeanMetadata metadata, Method method) {
        return metadata.runtime().resolverOfAnnotations().resolve(method).isAnnotationPresent(Resource.class);
    }

    @Override
    protected boolean isInitializer(BeanMetadata metadata, Method method) {
        return metadata.runtime().resolverOfAnnotations().resolve(method).isAnnotationPresent(PostConstruct.class);
    }

    @Override
    protected boolean isDestroyer(BeanMetadata metadata, Method method) {
        return metadata.runtime().resolverOfAnnotations().resolve(method).isAnnotationPresent(PreDestroy.class);
    }
}
