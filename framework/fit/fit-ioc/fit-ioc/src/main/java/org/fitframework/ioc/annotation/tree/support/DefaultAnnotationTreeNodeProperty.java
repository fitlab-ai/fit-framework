// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.annotation.tree.support;

import org.fitframework.ioc.annotation.AnnotationDeclarationException;
import org.fitframework.ioc.annotation.tree.AnnotationTreeNode;
import org.fitframework.ioc.annotation.tree.AnnotationTreeNodeProperty;
import org.fitframework.ioc.annotation.tree.AnnotationTreeNodePropertySource;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Queue;

/**
 * 为注解树的节点提供属性定义。
 *
 * @author 梁济时
 * @since 2022-05-03
 */
class DefaultAnnotationTreeNodeProperty implements AnnotationTreeNodeProperty {
    private final AnnotationTreeNode node;
    private final String name;
    private final Object defaultValue;
    private final Object value;

    /**
     * 使用属性所属节点、名称、默认值和实际值初始化 {@link DefaultAnnotationTreeNodeProperty} 类的新实例。
     *
     * @param node 表示属性所属节点的 {@link AnnotationTreeNode}。
     * @param name 表示属性名称的 {@link String}。
     * @param defaultValue 表示属性的默认值的 {@link Object}。
     * @param value 表示属性的实际值的 {@link Object}。
     */
    DefaultAnnotationTreeNodeProperty(AnnotationTreeNode node, String name, Object defaultValue, Object value) {
        this.node = node;
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = value;
    }

    @Override
    public AnnotationTreeNode node() {
        return this.node;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Object defaultValue() {
        return this.defaultValue;
    }

    @Override
    public Object value() {
        return this.value;
    }

    @Override
    public List<AnnotationTreeNodeProperty> sources() {
        List<AnnotationTreeNodeProperty> properties = new ArrayList<>();
        properties.add(this);
        Queue<AnnotationTreeNodePropertySource> sources = new LinkedList<>(this.node().sources(this.name()));
        while (!sources.isEmpty()) {
            AnnotationTreeNodePropertySource source = sources.poll();
            AnnotationTreeNodeProperty property = source.node().property(source.property())
                    .orElseThrow(() -> new AnnotationDeclarationException(String.format(Locale.ROOT,
                            "No property in attribute to forward. [source=%s.%s(), target=%s.%s()]",
                            source.node().type().getName(), source.property(),
                            this.node().type().getName(), this.name())));
            property = new ConvertedAnnotationTreeNodeProperty(property, source.converter());
            if (!properties.contains(property)) {
                properties.add(property);
                sources.addAll(property.node().sources(property.name()));
            }
        }
        return properties;
    }
}
