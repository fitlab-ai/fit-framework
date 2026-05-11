// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.inspection;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 可以为 {@code null} 标记的注解。
 *
 * @author 季聿阶
 * @since 2022-05-27
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Nullable {}
