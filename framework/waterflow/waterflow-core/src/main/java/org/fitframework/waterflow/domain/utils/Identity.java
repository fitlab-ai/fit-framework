// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.waterflow.domain.utils;

/**
 * ID生成器接口
 *
 * @author 高诗意
 * @since 1.0
 */
@FunctionalInterface
public interface Identity {
    /**
     * getId
     *
     * @return String
     */
    String getId();
}
