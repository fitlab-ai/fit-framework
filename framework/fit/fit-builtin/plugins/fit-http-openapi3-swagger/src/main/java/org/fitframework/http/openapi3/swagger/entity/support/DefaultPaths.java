// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.openapi3.swagger.entity.support;

import org.fitframework.http.openapi3.swagger.entity.PathItem;
import org.fitframework.http.openapi3.swagger.entity.Paths;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 表示 {@link Paths} 的默认实现。
 *
 * @author 季聿阶
 * @since 2023-08-21
 */
public class DefaultPaths implements Paths {
    private final Map<String, PathItem> paths = new HashMap<>();

    @Override
    public boolean contains(String path) {
        return this.paths.containsKey(path);
    }

    @Override
    public void put(String path, PathItem pathItem) {
        this.paths.put(path, pathItem);
    }

    @Override
    public PathItem get(String path) {
        return this.paths.get(path);
    }

    @Override
    public Map<String, PathItem> getPathItems() {
        return this.paths;
    }

    @Override
    public Map<String, Object> toJson() {
        return this.paths.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().toJson()));
    }
}
