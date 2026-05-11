// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.annotation.support;

import org.fitframework.ioc.annotation.AnnotationEliminator;

import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;

/**
 * 为 {@link AnnotationEliminator} 提供组合模式的实现。
 *
 * @author 梁济时
 * @since 2022-05-19
 */
public class AnnotationEliminatorComposite implements AnnotationEliminator {
    private final List<AnnotationEliminator> eliminators;

    /**
     * 初始化 {@link AnnotationEliminatorComposite} 类的新实例。
     */
    public AnnotationEliminatorComposite() {
        this.eliminators = new LinkedList<>();
    }

    /**
     * 添加一个 {@link AnnotationEliminator} 到组合中。
     *
     * @param eliminator 表示要添加的 {@link AnnotationEliminator}。
     */
    public void add(AnnotationEliminator eliminator) {
        if (eliminator != null) {
            this.eliminators.add(eliminator);
        }
    }

    /**
     * 从组合中移除一个 {@link AnnotationEliminator}。
     *
     * @param eliminator 表示要移除的 {@link AnnotationEliminator}。
     */
    public void remove(AnnotationEliminator eliminator) {
        if (eliminator != null) {
            this.eliminators.remove(eliminator);
        }
    }

    @Override
    public boolean eliminate(Annotation annotation) {
        for (AnnotationEliminator eliminator : this.eliminators) {
            if (eliminator.eliminate(annotation)) {
                return true;
            }
        }
        return false;
    }
}
