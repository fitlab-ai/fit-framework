// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.support;

import org.fitframework.ioc.BeanMetadata;
import org.fitframework.ioc.DependencyResolver;
import org.fitframework.ioc.DependencyResolvingResult;
import org.fitframework.ioc.annotation.AnnotationMetadata;
import org.fitframework.ioc.lifecycle.bean.ValueSupplier;

import java.lang.reflect.Type;

/**
 * 为 {@link DependencyRequirement} 提供基于对 Bean 的依赖的实现。
 *
 * @author 梁济时
 * @since 2022-12-26
 */
final class BeanDependencyRequirement extends DependencyRequirement {
    private final String alias;

    /**
     * 使用待注入依赖的 Bean 的元数据，及所依赖的 Bean 的别名初始化 {@link BeanDependencyRequirement} 类的新实例。
     *
     * @param source 表示待注入依赖的 Bean 的元数据的 {@link BeanMetadata}。
     * @param alias 表示所依赖的 Bean 的别名的 {@link String}。
     * @throws IllegalArgumentException {@code source} 为 {@code null}。
     */
    BeanDependencyRequirement(BeanMetadata source, String alias) {
        super(source);
        this.alias = alias;
    }

    @Override
    ValueSupplier withType(Type targetType, AnnotationMetadata annotations) {
        return new Supplier(this.source(), this.alias, targetType, annotations);
    }

    private static final class Supplier implements ValueSupplier {
        private final BeanMetadata source;
        private final String alias;
        private final Type type;
        private final AnnotationMetadata annotations;

        private Supplier(BeanMetadata source, String alias, Type type, AnnotationMetadata annotations) {
            this.source = source;
            this.alias = alias;
            this.type = type;
            this.annotations = annotations;
        }

        @Override
        public Object get() {
            DependencyResolver resolver = this.source.runtime().resolverOfDependencies();
            DependencyResolvingResult result = resolver.resolve(this.source, this.alias, this.type, this.annotations);
            if (result.resolved()) {
                return result.get();
            }
            return null;
        }
    }
}
