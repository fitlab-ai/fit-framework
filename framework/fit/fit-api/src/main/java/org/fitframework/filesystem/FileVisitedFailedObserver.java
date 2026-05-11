// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.filesystem;

import java.io.File;
import java.io.IOException;

/**
 * 表示访问文件失败的观察者。
 *
 * @author 季聿阶
 * @since 2023-07-26
 */
@FunctionalInterface
public interface FileVisitedFailedObserver {
    /**
     * 获取空的观察者。
     *
     * @return 表示空的观察者的 {@link FileVisitedFailedObserver}。
     */
    static FileVisitedFailedObserver empty() {
        return (file, exception) -> {};
    }

    /**
     * 表示当文件访问失败时，触发的事件。
     *
     * @param file 表示访问失败的文件的 {@link File}。
     * @param exception 表示访问失败时发生的异常的 {@link IOException}。
     */
    void onFileVisitedFailed(File file, IOException exception);
}
