// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.filesystem;

import java.io.File;

/**
 * 表示文件目录树遍历完成的观察者。
 *
 * @author 季聿阶
 * @since 2023-11-23
 */
@FunctionalInterface
public interface FileTreeVisitedObserver {
    /**
     * 获取空的观察者。
     *
     * @return 表示空的观察者的 {@link FileTreeVisitedObserver}。
     */
    static FileTreeVisitedObserver empty() {
        return file -> {};
    }

    /**
     * 表示当整个文件目录树访问完成时，触发的事件。
     *
     * @param root 表示整个文件目录树的根文件的 {@link File}。
     */
    void onFileTreeVisited(File root);
}
