// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.jvm.classfile.attribute;

import org.fitframework.inspection.Validation;
import org.fitframework.jvm.classfile.AttributeInfo;

/**
 * 为属性提供基类。
 * TODO 待所有类型的属性都实现后，直接继承自 {@link AttributeInfo}，删除该类型。
 *
 * @author 梁济时
 * @since 2022-06-10
 */
public abstract class AbstractAttribute {
    private final AttributeInfo attribute;

    /**
     * 为属性内容提供基类。
     *
     * @param attribute 表示所属的属性的 {@link AttributeInfo}。
     */
    public AbstractAttribute(AttributeInfo attribute) {
        this.attribute = Validation.notNull(attribute, "The owning attribute cannot be null.");
    }

    /**
     * 获取所属的属性。
     *
     * @return 表示所属属性的 {@link AttributeInfo}。
     */
    public final AttributeInfo attribute() {
        return this.attribute;
    }
}
