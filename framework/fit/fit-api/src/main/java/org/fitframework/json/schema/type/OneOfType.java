// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.json.schema.type;

import static org.fitframework.inspection.Validation.notEmpty;
import static org.fitframework.inspection.Validation.notNull;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 表示 JsonSchema 中 OneOf 的类型。
 *
 * @author 季聿阶
 * @since 2024-10-22
 */
public class OneOfType implements Type {
    private final List<Type> types;

    /**
     * 使用指定的类型数组初始化 {@link OneOfType} 的新实例。
     *
     * @param types 表示类型的 {@link Type} 数组。
     * @throws IllegalArgumentException 当 {@code types} 为 {@code null} 时。
     */
    public OneOfType(Type... types) {
        notNull(types, "The types cannot be null.");
        this.types = Collections.unmodifiableList(Arrays.asList(types));
    }

    /**
     * 使用指定的类型列表初始化 {@link OneOfType} 的新实例。
     *
     * @param types 表示类型的 {@link List}{@code <}{@link Type}{@code >}。
     * @throws IllegalArgumentException 当 {@code types} 为 {@code null} 或空时。
     */
    public OneOfType(List<Type> types) {
        notEmpty(types, "The types cannot be empty.");
        this.types = Collections.unmodifiableList(types);
    }

    /**
     * 获取类型列表。
     *
     * @return 表示类型列表的 {@link List}{@code <}{@link Type}{@code >}。
     */
    public List<Type> types() {
        return this.types;
    }

    @Override
    public String getTypeName() {
        return this.types.stream().map(Type::getTypeName).collect(Collectors.joining(", ", "OneOf{", "}"));
    }

    @Override
    public boolean equals(Object another) {
        if (this == another) {
            return true;
        }
        if (another == null || this.getClass() != another.getClass()) {
            return false;
        }
        OneOfType oneOfType = (OneOfType) another;
        return Objects.equals(this.types, oneOfType.types);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.types);
    }

    @Override
    public String toString() {
        return this.getTypeName();
    }
}
