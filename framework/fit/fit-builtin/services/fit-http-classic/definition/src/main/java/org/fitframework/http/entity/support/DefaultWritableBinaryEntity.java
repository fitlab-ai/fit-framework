// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.entity.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.HttpMessage;
import org.fitframework.http.entity.WritableBinaryEntity;
import org.fitframework.http.protocol.MimeType;
import org.fitframework.http.protocol.ServerResponse;
import org.fitframework.inspection.Nonnull;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 表示 {@link WritableBinaryEntity} 的默认实现。
 *
 * @author 季聿阶
 * @since 2023-01-28
 */
public class DefaultWritableBinaryEntity extends AbstractEntity implements WritableBinaryEntity {
    private final ServerResponse serverResponse;

    /**
     * 创建只写的二进制的消息体数据对象。
     *
     * @param httpMessage 表示消息体数据所属的 Http 消息的 {@link HttpMessage}。
     * @param serverResponse 表示服务端的 Http 响应的 {@link ServerResponse}，用于消息体数据的写入。
     */
    public DefaultWritableBinaryEntity(HttpMessage httpMessage, ServerResponse serverResponse) {
        super(httpMessage);
        this.serverResponse = notNull(serverResponse, "The server response cannot be null.");
    }

    @Override
    public void write(byte[] bytes, int off, int len) throws IOException {
        this.serverResponse.writeBody(bytes, off, len);
    }

    @Override
    public void flush() throws IOException {
        this.serverResponse.flush();
    }

    @Override
    public OutputStream getOutputStream() {
        return this.serverResponse.getBodyOutputStream();
    }

    @Nonnull
    @Override
    public MimeType resolvedMimeType() {
        return MimeType.APPLICATION_OCTET_STREAM;
    }
}
