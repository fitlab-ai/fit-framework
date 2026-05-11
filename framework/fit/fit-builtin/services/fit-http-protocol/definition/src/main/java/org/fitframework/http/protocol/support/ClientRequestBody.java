// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.protocol.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.protocol.ClientRequest;
import org.fitframework.http.protocol.WritableMessageBody;
import org.fitframework.inspection.Nonnull;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 表示 {@link WritableMessageBody} 在 {@link ClientRequest} 中的默认实现。
 *
 * @author 季聿阶
 * @since 2023-09-19
 */
public class ClientRequestBody extends OutputStream implements WritableMessageBody {
    private final ClientRequest request;

    public ClientRequestBody(ClientRequest request) {
        this.request = notNull(request, "The client request cannot be null.");
    }

    @Override
    public void write(int b) throws IOException {
        this.request.writeBody(b);
    }

    @Override
    public void write(@Nonnull byte[] bytes, int off, int len) throws IOException {
        this.request.writeBody(bytes, off, len);
    }

    @Override
    public void flush() throws IOException {
        this.request.flush();
    }
}
