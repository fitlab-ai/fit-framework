// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.client.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.client.Response;
import org.fitframework.serialization.ResponseMetadata;

/**
 * 表示 {@link Response} 的默认实现。
 *
 * @author 季聿阶
 * @since 2022-09-19
 */
public class DefaultResponse implements Response {
    private final ResponseMetadata metadata;
    private final Object data;

    public DefaultResponse(ResponseMetadata metadata, Object data) {
        this.metadata = notNull(metadata, "The metadata cannot be null.");
        this.data = data;
    }

    @Override
    public ResponseMetadata metadata() {
        return this.metadata;
    }

    @Override
    public Object data() {
        return this.data;
    }
}
