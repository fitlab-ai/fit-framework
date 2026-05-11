// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.serialization.util;

import org.fitframework.flowable.Choir;
import org.fitframework.flowable.Solo;
import org.fitframework.inspection.Nonnull;
import org.fitframework.util.TypeUtils;

import java.lang.reflect.Type;

/**
 * 表示某种类型作为发布者的类别。
 *
 * @author 何天放
 * @since 2024-05-13
 */
public enum PublisherCategory {
    /** 表示非响应式流类型。 */
    NON_PUBLISHER(-1),

    /** 表示 Choir 响应式流类型。 */
    CHOIR(0),

    /** 表示 Solo 响应式流类型。 */
    SOLO(1);

    private final int code;

    PublisherCategory(int code) {
        this.code = code;
    }

    int code() {
        return this.code;
    }

    /**
     * 通过给定类型获取发布者种类。
     *
     * @param type 表示发布者种类的 {@link Type}。
     * @return 表示发布者种类的 {@link PublisherCategory}。
     */
    @Nonnull
    public static PublisherCategory fromType(Type type) {
        if (type == null) {
            return NON_PUBLISHER;
        }
        Class<?> clazz = TypeUtils.toClass(type);
        if (Choir.class.isAssignableFrom(clazz)) {
            return CHOIR;
        } else if (Solo.class.isAssignableFrom(clazz)) {
            return SOLO;
        } else {
            return NON_PUBLISHER;
        }
    }
}
