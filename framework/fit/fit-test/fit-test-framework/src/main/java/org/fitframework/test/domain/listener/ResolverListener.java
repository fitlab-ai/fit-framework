// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.test.domain.listener;

import org.fitframework.test.domain.resolver.TestClassResolver;
import org.fitframework.test.domain.resolver.TestContextConfiguration;

import java.util.Optional;

/**
 * 用于 Resolver 配置解析的监听器。
 *
 * @author 易文渊
 * @since 2024-07-21
 */
public class ResolverListener implements TestListener {
    @Override
    public Optional<TestContextConfiguration> config(Class<?> clazz) {
        TestContextConfiguration configuration = TestClassResolver.create().resolve(clazz);
        return Optional.of(configuration);
    }
}
