// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.service;

import org.fitframework.http.annotation.DocumentIgnored;
import org.fitframework.http.annotation.GetMapping;
import org.fitframework.http.annotation.RequestMapping;
import org.fitframework.service.server.RegistryServer;
import org.fitframework.annotation.Component;
import org.fitframework.util.MapBuilder;

import java.util.Map;

/**
 * 用于注册中心的检查。
 *
 * @author 季聿阶
 * @since 2023-06-30
 */
@Component
@RequestMapping("/fit")
public class CheckController {
    private final RegistryServer server;

    public CheckController(RegistryServer server) {
        this.server = server;
    }

    /**
     * 获取所有服务的信息。
     *
     * @return 表示所有服务信息的 {@link Map}{@code <}{@link String}{@code , }{@link String}{@code >}。
     */
    @DocumentIgnored
    @GetMapping("/check")
    public Map<String, Object> getAllServices() {
        return MapBuilder.<String, Object>get()
                .put("workers", this.server.getWorkers())
                .put("applications", this.server.getApplications())
                .put("workerApplications", this.server.getWorkerApplications())
                .put("applicationMetas", this.server.getApplicationMetas())
                .build();
    }
}
