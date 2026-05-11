// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.netty;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

/**
 * 表示请求消息体的缓冲区。
 *
 * @author 季聿阶
 * @since 2023-09-30
 */
public interface NettyReadableMessageBodyBuffer {
    /**
     * 向当前可读消息体中写入数据，供后续读取。
     *
     * @param data 表示待写入数据的 {@link ByteBuf}。
     * @param isLast 表示待写入数据是否为最后一块数据的标志的 {@code boolean}。
     * @throws IOException 当发生 I/O 异常时。
     */
    void write(ByteBuf data, boolean isLast) throws IOException;
}
