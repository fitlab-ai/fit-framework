// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.runtime;

import org.fitframework.protocol.jar.JarLocation;
import org.fitframework.runtime.aggregated.AggregatedFitRuntime;
import org.fitframework.runtime.direct.DirectFitRuntime;
import org.fitframework.runtime.discrete.DiscreteFitRuntime;
import org.fitframework.util.ClassUtils;

import java.net.URL;
import java.util.UUID;

/**
 * 为 FIT 应用程序提供启动程序。
 *
 * @author 梁济时
 * @since 2023-02-06
 */
public final class FitStarter {
    /**
     * 隐藏默认构造方法，避免工具类被实例化。
     */
    private FitStarter() {}

    /**
     * 启动运行时环境。
     *
     * @param entry 表示入口类的 {@link Class}。
     * @param args 表示命令行参数的 {@link String}{@code []}。
     * @return 表示新启动的运行时环境的 {@link FitRuntime}。
     */
    public static FitRuntime start(Class<?> entry, String[] args) {
        URL url = ClassUtils.locateOfProtectionDomain(FitStarter.class);
        JarLocation location = JarLocation.parse(url);
        FitRuntime runtime;
        if (!location.nests().isEmpty()) {
            runtime = new AggregatedFitRuntime(entry, args);
        } else if (FitStarter.class.getClassLoader() == ClassLoader.getSystemClassLoader()) {
            runtime = new DirectFitRuntime(entry, args);
        } else {
            runtime = new DiscreteFitRuntime(entry, args);
        }
        System.setProperty("worker.instance-id", createInstanceId());
        runtime.start();
        return runtime;
    }

    private static String createInstanceId() {
        return UUID.randomUUID().toString();
    }
}
