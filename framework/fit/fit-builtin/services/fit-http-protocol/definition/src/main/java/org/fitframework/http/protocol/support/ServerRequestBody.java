// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.protocol.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.protocol.ReadableMessageBody;
import org.fitframework.http.protocol.ServerRequest;
import org.fitframework.inspection.Nonnull;

import java.io.IOException;
import java.io.InputStream;

/**
 * 表示 {@link ReadableMessageBody} 在 {@link ServerRequest} 中的默认实现。
 *
 * @author 季聿阶
 * @since 2023-09-19
 */
public class ServerRequestBody extends InputStream implements ReadableMessageBody {
    private final ServerRequest request;

    public ServerRequestBody(ServerRequest request) {
        this.request = notNull(request, "The server request cannot be null.");
    }

    @Override
    public int read() throws IOException {
        return this.request.readBody();
    }

    @Override
    public int read(@Nonnull byte[] bytes, int off, int len) throws IOException {
        return this.request.readBody(bytes, off, len);
    }
}
