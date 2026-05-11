// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.entity.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.HttpMessage;
import org.fitframework.http.entity.Entity;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * 表示 {@link Entity} 的抽象实现。
 * <p>每一个 {@link Entity} 都必然属于一个 {@link HttpMessage}。</p>
 *
 * @author 季聿阶
 * @since 2022-08-03
 */
public abstract class AbstractEntity implements Entity {
    private final HttpMessage httpMessage;

    /**
     * 通过 Http 消息来实例化 {@link AbstractEntity}。
     *
     * @param httpMessage 表示 Http 消息的 {@link HttpMessage}。
     * @throws IllegalArgumentException 当 {@code httpMessage} 为 {@code null} 时。
     */
    protected AbstractEntity(HttpMessage httpMessage) {
        this.httpMessage = notNull(httpMessage, "The http message cannot be null.");
    }

    @Override
    public HttpMessage belongTo() {
        return this.httpMessage;
    }

    @Override
    public Map<String, String> resolvedParameters() {
        return Collections.emptyMap();
    }

    @Override
    public void close() throws IOException {}
}
