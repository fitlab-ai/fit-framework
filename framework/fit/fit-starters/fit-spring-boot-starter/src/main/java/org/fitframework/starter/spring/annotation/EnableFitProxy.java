// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.starter.spring.annotation;

import org.fitframework.starter.spring.FitProxyRegistrar;
import org.fitframework.broker.Genericable;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用 FIT 代理生成功能的注解。
 * <p>该注解用于扫描指定包路径下的类，并注册包含 {@link Genericable} 注解的接口方法，可通过 {@link #basePackages()} 配置扫描路径。</p>
 *
 * @author 杭潇
 * @since 2025-02-22
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(FitProxyRegistrar.class)
public @interface EnableFitProxy {
    /**
     * 配置待扫描的包路径。
     * <p>默认为空，若未配置则不会自动扫描。</p>
     *
     * @return 返回配置的包路径的 {@link String}{@code []}。
     */
    String[] basePackages() default {};
}
