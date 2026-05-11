// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.heartbeat.client;

import static org.fitframework.inspection.Validation.greaterThan;
import static org.fitframework.inspection.Validation.greaterThanOrEquals;
import static org.fitframework.inspection.Validation.notBlank;
import static org.fitframework.inspection.Validation.notNull;
import static org.fitframework.util.ObjectUtils.nullIf;

import org.fitframework.heartbeat.HeartbeatMonitor;
import org.fitframework.heartbeat.HeartbeatService;
import org.fitframework.annotation.Component;
import org.fitframework.conf.runtime.WorkerConfig;
import org.fitframework.log.Logger;
import org.fitframework.runtime.FitRuntime;
import org.fitframework.runtime.FitRuntimeStartedObserver;
import org.fitframework.schedule.ExecutePolicy;
import org.fitframework.schedule.Task;
import org.fitframework.schedule.ThreadPoolScheduler;
import org.fitframework.util.LockUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * 为心跳提供客户端。
 *
 * @author 夏斐
 * @author 季聿阶
 * @since 2020-08-19
 */
@Component
public class DefaultHeartbeatMonitor implements HeartbeatMonitor, FitRuntimeStartedObserver {
    private static final Logger log = Logger.get(DefaultHeartbeatMonitor.class);

    private final HeartbeatService heartbeatService;

    private final WorkerConfig worker;
    private final Map<String, HeartbeatInfo> heartbeatInfos = new HashMap<>();
    private final ThreadPoolScheduler keepAliveScheduledExecutor;
    private final ReadWriteLock lock = LockUtils.newReentrantReadWriteLock();

    public DefaultHeartbeatMonitor(HeartbeatService heartbeatService, WorkerConfig worker) {
        this.heartbeatService = notNull(heartbeatService, "The heartbeat service cannot be null.");
        this.worker = notNull(worker, "The worker config cannot be null.");
        this.keepAliveScheduledExecutor = ThreadPoolScheduler.custom()
                .corePoolSize(1)
                .isDaemonThread(true)
                .threadPoolName("heartbeat-client")
                .build();
    }

    @Override
    public void onRuntimeStarted(FitRuntime runtime) {
        log.debug("Start scheduled task to heart beat.");
        this.keepAliveScheduledExecutor.schedule(Task.builder()
                .runnable(this::keepAlive)
                .policy(ExecutePolicy.fixedDelay(100))
                .uncaughtExceptionHandler((thread, exception) -> this.fallbackForKeepAlive(exception))
                .build(), 0);
    }

    private void fallbackForKeepAlive(Throwable cause) {
        log.warn("Failed to keep alive. [cause={}]", cause.getMessage());
        log.debug("Failed to keep alive.", cause);
    }

    @Override
    public void keepAlive(String scenario, int period, int initialExtraAliveTime, int aliveTime) {
        notBlank(scenario, "The scenario to keep alive cannot be blank.");
        greaterThan(period, 0, "The period to keep alive must be positive.");
        greaterThanOrEquals(initialExtraAliveTime, 0, "The initial extra alive time cannot be negative.");
        greaterThan(aliveTime, 0, "The alive time must be positive.");
        LockUtils.synchronize(this.lock.writeLock(),
                () -> this.heartbeatInfos.put(scenario,
                        HeartbeatInfo.create(scenario, period, initialExtraAliveTime, aliveTime)));
    }

    @Override
    public void terminate(String scenario) {
        notBlank(scenario, "The scenario to terminate cannot be blank.");
        LockUtils.synchronize(this.lock.writeLock(), () -> {
            this.heartbeatInfos.remove(scenario);
            HeartbeatService.HeartbeatInfo info = new HeartbeatService.HeartbeatInfo();
            info.setSceneType(scenario);
            this.disconnect(info);
        });
    }

    private void keepAlive() {
        LockUtils.synchronize(this.lock.readLock(), () -> this.heartbeatInfos.values().forEach(this::keepAlive));
    }

    private void keepAlive(HeartbeatInfo heartbeatInfo) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - heartbeatInfo.lastSyncTime() < heartbeatInfo.period() * 1000L) {
            return;
        }
        HeartbeatService.HeartbeatInfo info = new HeartbeatService.HeartbeatInfo();
        info.setSceneType(heartbeatInfo.scenario());
        info.setInitDelay(heartbeatInfo.initialExtraAliveTime() * 1000L);
        info.setAliveTime(heartbeatInfo.aliveTime() * 1000L);
        boolean isConnected = this.connect(info);
        if (isConnected) {
            heartbeatInfo.lastSyncTime(currentTime);
        }
    }

    private boolean connect(HeartbeatService.HeartbeatInfo heartbeatInfo) {
        try {
            log.debug("Prepare to connect with heartbeat server. [sceneType={}]", heartbeatInfo.getSceneType());
            boolean isConnected = nullIf(this.heartbeatService.sendHeartbeat(Collections.singletonList(heartbeatInfo),
                    this.getAddress()), false);
            log.debug("Connect with heartbeat server. [sceneType={}, result={}]",
                    heartbeatInfo.getSceneType(),
                    isConnected);
            return isConnected;
        } catch (Exception e) {
            log.error("Failed to connect with heartbeat server.", e);
            return false;
        }
    }

    private void disconnect(HeartbeatService.HeartbeatInfo heartbeatInfo) {
        try {
            log.info("Prepare to disconnect with heartbeat server. [sceneType={}]", heartbeatInfo.getSceneType());
            boolean isDisconnected =
                    nullIf(this.heartbeatService.stopHeartbeat(Collections.singletonList(heartbeatInfo),
                            this.getAddress()), false);
            log.info("Disconnect with heartbeat server. [sceneType={}, result={}]",
                    heartbeatInfo.getSceneType(),
                    isDisconnected);
        } catch (Exception e) {
            log.error("Failed to disconnect with heartbeat server.", e);
        }
    }

    private HeartbeatService.Address getAddress() {
        HeartbeatService.Address address = new HeartbeatService.Address();
        address.setId(this.worker.id());
        return address;
    }
}
