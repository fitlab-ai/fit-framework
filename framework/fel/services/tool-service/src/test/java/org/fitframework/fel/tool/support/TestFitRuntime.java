// SPDX-License-Identifier: MIT
// Copyright (c) 2025-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.tool.support;

import org.fitframework.http.server.HttpClassicServer;
import org.fitframework.ioc.BeanFactory;
import org.fitframework.runtime.FitRuntime;
import org.fitframework.runtime.FitStarter;
import org.fitframework.util.StringUtils;
import org.fitframework.util.ThreadUtils;

/**
 * 用于测试的统一 FIT 运行时。
 *
 * @author 何天放
 * @since 2024-06-15
 */
public class TestFitRuntime {
    /** 表示 FIT 统一测试运行时的单例。 */
    public static final TestFitRuntime INSTANCE = new TestFitRuntime();

    private FitRuntime runtime;

    private volatile boolean started = false;

    private TestFitRuntime() {}

    public boolean started() {
        return this.started;
    }

    /**
     * 启动 FIT 运行时。
     *
     * @param port 表示端口的 {@code int}。
     */
    public void start(int port) {
        if (this.started()) {
            return;
        }
        this.runtime =
                FitStarter.start(HttpToolTest.class, new String[] {StringUtils.format("server.http.port={0}", port)});
        HttpClassicServer httpClassicServer = runtime.root()
                .container()
                .lookup(HttpClassicServer.class)
                .map(BeanFactory::<HttpClassicServer>get)
                .orElseThrow(() -> new IllegalStateException("Failed to start http server."));
        while (!httpClassicServer.isStarted()) {
            ThreadUtils.sleep(0);
        }
        this.started = true;
    }

    /**
     * 终止 FIT 运行时。
     */
    public void stop() {
        HttpClassicServer httpClassicServer = this.runtime.root()
                .container()
                .lookup(HttpClassicServer.class)
                .map(BeanFactory::<HttpClassicServer>get)
                .orElseThrow(() -> new IllegalStateException("Failed to stop http server."));
        httpClassicServer.stop();
        this.started = false;
    }
}

