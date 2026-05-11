// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.test.domain.listener;

import org.fitframework.test.annotation.EnableMybatis;
import org.fitframework.test.domain.resolver.TestContextConfiguration;
import org.fitframework.test.domain.util.AnnotationUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * 用于字段注入 mybatis 的监听器。
 *
 * @author 易文渊
 * @since 2024-07-21
 */
public class MybatisTestListener implements TestListener {
    private static final Set<String> DEFAULT_SCAN_PACKAGES =
            new HashSet<>(Arrays.asList("org.fitframework.transaction", "org.fitframework.integration.mybatis"));

    @Override
    public Optional<TestContextConfiguration> config(Class<?> clazz) {
        if (!AnnotationUtils.getAnnotation(clazz, EnableMybatis.class).isPresent()) {
            return Optional.empty();
        }
        TestContextConfiguration configuration =
                TestContextConfiguration.custom().testClass(clazz).scannedPackages(DEFAULT_SCAN_PACKAGES).build();
        return Optional.of(configuration);
    }
}