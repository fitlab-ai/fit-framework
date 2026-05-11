// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor;

import org.fitframework.exception.ExceptionHandler;
import org.fitframework.inspection.Nullable;

import java.util.concurrent.Executor;

/**
 * 表示异步执行的配置器。
 *
 * @author 季聿阶
 * @since 2022-11-11
 */
public interface AsyncConfigurer {
    /**
     * 获取默认异步执行的线程池。
     *
     * @return 表示默认异步执行的线程池的 {@link Executor}。
     */
    @Nullable
    default Executor getExecutor() {
        return null;
    }

    /**
     * 获取默认异步执行线程池的异常处理器。
     *
     * @return 表示默认异步执行线程池的异常处理器的 {@link ExceptionHandler}。
     */
    @Nullable
    default ExceptionHandler getUncaughtExceptionHandler() {
        return null;
    }
}
