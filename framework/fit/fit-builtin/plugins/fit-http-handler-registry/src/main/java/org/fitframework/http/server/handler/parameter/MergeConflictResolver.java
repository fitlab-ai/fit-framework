// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.parameter;

import org.fitframework.merge.Conflict;
import org.fitframework.merge.ConflictResolver;

/**
 * 表示对 {@link String} 类型的冲突处理器。
 *
 * @author 季聿阶
 * @since 2022-08-11
 */
public class MergeConflictResolver implements ConflictResolver<String, String, Conflict<String>> {
    @Override
    public Result<String> resolve(String v1, String v2, Conflict<String> context) {
        String merged = v1 + "," + v2;
        return Result.<String>builder().resolved(true).result(merged).build();
    }
}
