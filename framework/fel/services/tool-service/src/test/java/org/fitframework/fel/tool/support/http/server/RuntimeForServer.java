// SPDX-License-Identifier: MIT
// Copyright (c) 2025-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.tool.support.http.server;

import org.fitframework.http.server.HttpClassicServer;
import org.fitframework.ioc.BeanFactory;
import org.fitframework.runtime.FitRuntime;
import org.fitframework.runtime.FitStarter;
import org.fitframework.util.ThreadUtils;

/**
 * 为测试 Http 提供的服务运行时。
 *
 * @author 王攀博
 * @since 2024-06-15
 */
public class RuntimeForServer {
    private volatile boolean isStarted = false;

    private final Class<?> entry;
    private FitRuntime runtime;

    public RuntimeForServer(Class<?> entry) {
        this.entry = entry;
    }

    /**
     * 表示启动 Http 客户端。
     *
     * @param port 表示可用的端口 {@link int}。
     */
    public void start(int port) {
        if (this.isStarted) {
            return;
        }
        this.runtime = FitStarter.start(this.entry, new String[] {"server.http.port=" + port});
        HttpClassicServer httpClassicServer = runtime.root()
                .container()
                .lookup(HttpClassicServer.class)
                .map(BeanFactory::<HttpClassicServer>get)
                .orElseThrow(() -> new IllegalStateException("Failed to start http server."));
        while (!httpClassicServer.isStarted()) {
            ThreadUtils.sleep(0);
        }
        this.isStarted = true;
    }

    /**
     * 表示用于关闭 Http 客户端。
     */
    public void stop() {
        HttpClassicServer httpClassicServer = this.runtime.root()
                .container()
                .lookup(HttpClassicServer.class)
                .map(BeanFactory::<HttpClassicServer>get)
                .orElseThrow(() -> new IllegalStateException("Failed to stop http server."));
        httpClassicServer.stop();
        this.isStarted = false;
    }
}
