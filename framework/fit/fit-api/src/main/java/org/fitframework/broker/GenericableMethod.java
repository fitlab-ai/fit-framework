// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker;

import org.fitframework.inspection.Nullable;

import java.lang.reflect.Method;

/**
 * 表示服务方法。
 *
 * @author 季聿阶
 * @since 2023-03-29
 */
public interface GenericableMethod {
    /**
     * 获取服务的方法。
     *
     * @return 表示服务方法的 {@link Method}。
     */
    @Nullable
    Method method();
}
