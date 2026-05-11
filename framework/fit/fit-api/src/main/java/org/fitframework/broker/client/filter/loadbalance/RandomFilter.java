// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.client.filter.loadbalance;

import org.fitframework.broker.FitableMetadata;
import org.fitframework.broker.Target;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * 随机的负载均衡策略。
 *
 * @author 季聿阶
 * @since 2021-06-11
 */
public class RandomFilter extends ChampionFilter {
    private final Random random = new SecureRandom();

    @Override
    protected Optional<Target> select(FitableMetadata fitable, String localWorkerId, List<Target> toFilterTargets) {
        return Optional.of(toFilterTargets.get(this.random.nextInt(toFilterTargets.size())));
    }
}
