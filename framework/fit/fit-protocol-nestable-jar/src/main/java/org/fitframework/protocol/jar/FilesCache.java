// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.protocol.jar;

import org.fitframework.protocol.jar.support.WeakHashMapFilesCache;

import java.io.File;

/**
 * 表示文件缓存器的接口。
 *
 * @author 杭潇
 * @since 2025-02-11
 */
public interface FilesCache {
    /**
     * 根据给定文件获取其标准化值。
     *
     * @param file 表示给定文件的 {@link File}。
     * @return 获取标准化文件的 {@link File}。
     */
    File getCanonicalFile(File file);

    /**
     * 获取文件缓存器的实例。
     *
     * @return 表示文件缓存器实例的 {@link FilesCache}。
     */
    static FilesCache instance() {
        return WeakHashMapFilesCache.INSTANCE;
    }
}
