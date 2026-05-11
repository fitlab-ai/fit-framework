// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.protocol.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.protocol.ServerResponse;
import org.fitframework.http.protocol.WritableMessageBody;
import org.fitframework.inspection.Nonnull;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 表示 {@link WritableMessageBody} 在 {@link ServerResponse} 中的默认实现。
 *
 * @author 季聿阶
 * @since 2023-09-19
 */
public class ServerResponseBody extends OutputStream implements WritableMessageBody {
    private final ServerResponse response;

    public ServerResponseBody(ServerResponse response) {
        this.response = notNull(response, "The server response cannot be null.");
    }

    @Override
    public void write(int b) throws IOException {
        this.response.writeBody(b);
    }

    @Override
    public void write(@Nonnull byte[] bytes, int off, int len) throws IOException {
        this.response.writeBody(bytes, off, len);
    }

    @Override
    public void flush() throws IOException {
        this.response.flush();
    }
}
