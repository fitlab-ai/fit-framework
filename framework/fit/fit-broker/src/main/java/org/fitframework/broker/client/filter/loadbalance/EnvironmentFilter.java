// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.client.filter.loadbalance;

import static org.fitframework.inspection.Validation.notBlank;

import org.fitframework.broker.FitableMetadata;
import org.fitframework.broker.Target;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 指定环境标的负载均衡策略。
 *
 * @author 季聿阶
 * @since 2022-06-06
 */
public class EnvironmentFilter extends AbstractFilter {
    private final String environment;

    /**
     * 使用指定的环境初始化 {@link EnvironmentFilter} 的新实例。
     *
     * @param environment 表示环境的 {@link String}。
     * @throws IllegalArgumentException 当 {@code environment} 为 {@code null} 或空白字符串时。
     */
    public EnvironmentFilter(String environment) {
        this.environment = notBlank(environment, "The target environment to filter cannot be blank.");
    }

    @Override
    protected List<Target> loadbalance(FitableMetadata fitable, String localWorkerId, List<Target> toFilterTargets,
            Map<String, Object> extensions) {
        return toFilterTargets.stream()
                .filter(target -> Objects.equals(target.environment(), this.environment))
                .collect(Collectors.toList());
    }
}
