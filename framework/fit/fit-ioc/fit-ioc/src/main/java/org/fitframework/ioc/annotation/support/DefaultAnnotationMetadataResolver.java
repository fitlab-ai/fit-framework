// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.annotation.support;

import org.fitframework.ioc.annotation.AnnotationEliminator;
import org.fitframework.ioc.annotation.AnnotationMetadata;
import org.fitframework.ioc.annotation.AnnotationMetadataResolver;
import org.fitframework.ioc.annotation.AnnotationPropertyForwarder;
import org.fitframework.ioc.annotation.tree.AnnotationTree;
import org.fitframework.ioc.annotation.tree.AnnotationTreeNode;
import org.fitframework.ioc.annotation.tree.AnnotationTreeNodeContainer;
import org.fitframework.ioc.annotation.tree.ConverterCache;
import org.fitframework.ioc.annotation.tree.support.DefaultAnnotationTree;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.ServiceLoader;

/**
 * 为 {@link AnnotationMetadataResolver} 提供默认实现。
 *
 * @author 梁济时
 * @since 2022-05-03
 */
public class DefaultAnnotationMetadataResolver implements AnnotationMetadataResolver {
    /**
     * 表示空的注解元数据解析程序。
     */
    public static final AnnotationMetadataResolver EMPTY =
            element -> new DefaultAnnotationMetadata(Arrays.asList(element.getAnnotations()));

    private final AnnotationPropertyForwarder forwarder;
    private final AnnotationEliminator eliminator;

    /**
     * 初始化 {@link DefaultAnnotationMetadataResolver} 类的新实例。
     */
    public DefaultAnnotationMetadataResolver() {
        this.forwarder = loadForwarder();
        this.eliminator = loadEliminator();
    }

    private static AnnotationPropertyForwarder loadForwarder() {
        AnnotationPropertyForwarderComposite composite = new AnnotationPropertyForwarderComposite();
        load(AnnotationPropertyForwarder.class).forEach(composite::add);
        composite.add(new DefaultAnnotationPropertyForwarder());
        return composite;
    }

    private static AnnotationEliminator loadEliminator() {
        AnnotationEliminatorComposite composite = new AnnotationEliminatorComposite();
        load(AnnotationEliminator.class).forEach(composite::add);
        composite.add(new DefaultAnnotationEliminator());
        return composite;
    }

    private static <S> Iterable<S> load(Class<S> serviceClass) {
        return ServiceLoader.load(serviceClass, serviceClass.getClassLoader());
    }

    @Override
    public AnnotationMetadata resolve(AnnotatedElement element) {
        Annotation[] annotations = element.getDeclaredAnnotations();
        AnnotationTree tree = new DefaultAnnotationTree();
        for (Annotation annotation : annotations) {
            this.resolve(tree, tree, annotation);
        }
        return new DefaultAnnotationMetadata(tree.toAnnotations());
    }

    private void resolve(AnnotationTreeNodeContainer nodes, ConverterCache converters, Annotation annotation) {
        if (this.eliminator.eliminate(annotation)) {
            return;
        }

        AnnotationTreeNode node = nodes.createNode(annotation);
        nodes.add(node);

        Annotation[] children = annotation.annotationType().getDeclaredAnnotations();
        for (Annotation child : children) {
            this.resolve(node, converters, child);
        }

        Method[] methods = annotation.annotationType().getDeclaredMethods();
        for (Method method : methods) {
            this.forwarder.forward(method)
                    .ifPresent(forward -> node.forward(method.getName(),
                            forward.target(),
                            converters.get(forward.converterClass())));
        }
    }
}
