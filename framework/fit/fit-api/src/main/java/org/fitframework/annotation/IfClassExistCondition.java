// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.annotation;

import org.fitframework.ioc.BeanContainer;
import org.fitframework.ioc.Condition;
import org.fitframework.ioc.annotation.AnnotationMetadata;

/**
 * 为 {@link IfClassExist} 提供判定条件。
 *
 * @author 梁济时
 * @since 2023-05-18
 */
public class IfClassExistCondition implements Condition {
    @Override
    public boolean match(BeanContainer container, AnnotationMetadata annotations) {
        IfClassExist annotation = annotations.getAnnotation(IfClassExist.class);
        if (annotation == null) {
            return true;
        }
        String[] classNames = annotation.value();
        for (String className : classNames) {
            try {
                container.plugin().pluginClassLoader().loadClass(className);
            } catch (ClassNotFoundException ignored) {
                return false;
            }
        }
        return true;
    }
}
