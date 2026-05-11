// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.support;

import org.fitframework.http.server.HttpClassicServerRequest;
import org.fitframework.http.server.HttpClassicServerResponse;
import org.fitframework.http.server.handler.PropertyValueMapper;

import java.util.Map;

/**
 * 表示空的 Http 值映射器。
 *
 * @author 邬涨财
 * @since 2023-01-02
 */
public class EmptyPropertyValueMapper implements PropertyValueMapper {
    /** 表示空的 Http 值映射器的单例。 */
    public static final PropertyValueMapper INSTANCE = new EmptyPropertyValueMapper();

    private EmptyPropertyValueMapper() {}

    @Override
    public Object map(HttpClassicServerRequest request, HttpClassicServerResponse response,
            Map<String, Object> context) {
        return null;
    }
}
