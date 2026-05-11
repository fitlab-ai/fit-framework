// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.pipeline.huggingface.type;

import org.fitframework.resource.web.Media;
import org.fitframework.util.TypeUtils;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 表示 huggingface pipeline 的常量集合。
 *
 * @author 易文渊
 * @since 2024-06-06
 */
public interface Constant {
    /**
     * 表示 {@link List}{@code <}{@link Media}{@code >} 的 {@link Type}。
     */
    Type LIST_MEDIA_TYPE = TypeUtils.parameterized(List.class, new Type[] {Media.class});
}