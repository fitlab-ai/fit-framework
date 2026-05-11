// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.heartbeat.server;

import org.fitframework.heartbeat.HeartbeatService;
import org.fitframework.annotation.Component;
import org.fitframework.annotation.Fitable;

import java.util.List;

/**
 * Service for providing heartbeat-related functionality.
 *
 * @author 董智豪
 * @since 2025-06-04
 */
@Component
public class HeartbeatServer implements HeartbeatService {
    @Override
    @Fitable(id = "send-heartbeat")
    public Boolean sendHeartbeat(List<HeartbeatInfo> heartbeatInfo, Address address) {
        return true;
    }

    @Override
    @Fitable(id = "stop-heartbeat")
    public Boolean stopHeartbeat(List<HeartbeatInfo> heartbeatInfo, Address address) {
        return true;
    }
}