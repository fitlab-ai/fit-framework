// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines the authorization information for HTTP requests.
 * This annotation is used to specify the authentication details for an HTTP request.
 * It can be applied to methods annotated with HTTP mapping annotations (e.g., @GetMapping, @PostMapping)
 * to configure the authorization headers or tokens required for the request.
 *
 * @author 王攀博
 * @since 2025-01-24
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestAuthorization {}