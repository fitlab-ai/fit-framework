// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.protocol.jar.support;

import org.fitframework.protocol.jar.FilesCache;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;

import static org.fitframework.protocol.jar.location.Locations.path;

/**
 * 表示弱引用的文件缓存器实现。
 *
 * @author 杭潇
 * @since 2025-02-11
 */
public class WeakHashMapFilesCache implements FilesCache {
    /**
     * 表示当前类型的唯一实例。
     */
    public static final WeakHashMapFilesCache INSTANCE = new WeakHashMapFilesCache();

    private final Map<String, WeakReference<File>> weakCache;

    /**
     * 私有构造函数，防止外部实例化。
     */
    private WeakHashMapFilesCache() {
        this.weakCache = new WeakHashMap<>();
    }

    @Override
    public File getCanonicalFile(File file) {
        WeakReference<File> weakReference = this.weakCache.get(file.getAbsolutePath());
        File cachedFile = weakReference != null ? weakReference.get() : null;
        if (cachedFile == null) {
            try {
                cachedFile = file.getCanonicalFile();
                this.weakCache.put(file.getAbsolutePath(), new WeakReference<>(cachedFile));
            } catch (IOException e) {
                throw new IllegalArgumentException(String.format(Locale.ROOT,
                        "The file of JAR location is not canonical. [path=%s]",
                        path(file)));
            }
        }
        return cachedFile;
    }
}
