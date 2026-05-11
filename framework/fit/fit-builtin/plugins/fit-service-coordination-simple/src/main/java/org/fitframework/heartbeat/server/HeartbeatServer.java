// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.heartbeat.server;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.heartbeat.HeartbeatService;
import org.fitframework.service.WorkerCache;
import org.fitframework.annotation.Component;
import org.fitframework.annotation.Fitable;
import org.fitframework.util.CollectionUtils;
import org.fitframework.util.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 用于提供心跳相关的服务。
 *
 * @author 邬涨财
 * @since 2022-04-11
 */
@Component
public class HeartbeatServer implements HeartbeatService {
    private final WorkerCache cache;

    public HeartbeatServer(WorkerCache cache) {
        this.cache = notNull(cache, "The worker cache cannot be null.");
    }

    @Override
    @Fitable(id = "send-heartbeat")
    public Boolean sendHeartbeat(List<HeartbeatInfo> beatInfos, Address address) {
        if (address == null || StringUtils.isBlank(address.getId())) {
            return false;
        }
        if (CollectionUtils.isEmpty(beatInfos)) {
            return false;
        }
        HeartbeatInfo first = beatInfos.get(0);
        if (first == null || first.getAliveTime() == null || first.getAliveTime() <= 0) {
            return false;
        }
        Instant expireTime = Instant.now().plus(first.getAliveTime(), ChronoUnit.MILLIS);
        this.cache.refreshWorker(address.getId(), expireTime);
        return true;
    }

    @Override
    public Boolean stopHeartbeat(List<HeartbeatInfo> heartbeatInfo, Address address) {
        return true;
    }
}
