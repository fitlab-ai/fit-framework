// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.client.filter.loadbalance;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.broker.FitableMetadata;
import org.fitframework.broker.Target;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 每一个不同的 FIT 进程（Worker）都调用 1 次的负载均衡策略。
 *
 * @author 季聿阶
 * @since 2021-06-11
 */
public class WorkerFairFilter extends AbstractFilter {
    private final ChampionFilter championFilter;

    /**
     * 使用指定的冠军过滤器初始化 {@link WorkerFairFilter} 的新实例。
     *
     * @param championFilter 表示冠军过滤器的 {@link ChampionFilter}。
     * @throws IllegalArgumentException 当 {@code championFilter} 为 {@code null} 时。
     */
    public WorkerFairFilter(ChampionFilter championFilter) {
        this.championFilter = notNull(championFilter, "No championFilter.");
    }

    @Override
    protected List<Target> loadbalance(FitableMetadata fitable, String localWorkerId, List<Target> toFilterTargets,
            Map<String, Object> extensions) {
        return toFilterTargets.stream()
                .collect(Collectors.groupingBy(Target::workerId))
                .values()
                .stream()
                .map(workerTargets -> this.championFilter.select(fitable, localWorkerId, workerTargets))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
