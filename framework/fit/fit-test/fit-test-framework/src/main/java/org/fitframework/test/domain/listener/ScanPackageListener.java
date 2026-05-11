// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.test.domain.listener;

import org.fitframework.annotation.ScanPackages;
import org.fitframework.test.domain.resolver.TestContextConfiguration;
import org.fitframework.test.domain.util.AnnotationUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

/**
 * 表示包扫描器的监听器。
 *
 * @author 易文渊
 * @since 2024-07-26
 */
public class ScanPackageListener implements TestListener {
    @Override
    public Optional<TestContextConfiguration> config(Class<?> clazz) {
        return AnnotationUtils.getAnnotation(clazz, ScanPackages.class)
                .map(annotation -> TestContextConfiguration.custom()
                        .testClass(clazz)
                        .scannedPackages(new HashSet<>(Arrays.asList(annotation.value())))
                        .build());
    }
}