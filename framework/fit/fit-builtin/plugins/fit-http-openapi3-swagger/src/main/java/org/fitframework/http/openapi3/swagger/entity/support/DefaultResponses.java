// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.openapi3.swagger.entity.support;

import org.fitframework.http.openapi3.swagger.entity.Response;
import org.fitframework.http.openapi3.swagger.entity.Responses;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 表示 {@link Responses} 的默认实现。
 *
 * @author 季聿阶
 * @since 2023-08-27
 */
public class DefaultResponses implements Responses {
    private final Map<String, Response> responses = new HashMap<>();

    @Override
    public void put(String httpStatusCode, Response response) {
        this.responses.put(httpStatusCode, response);
    }

    @Override
    public Response get(String httpStatusCode) {
        return this.responses.get(httpStatusCode);
    }

    @Override
    public Map<String, Response> getResponses() {
        return Collections.unmodifiableMap(this.responses);
    }

    @Override
    public Map<String, Object> toJson() {
        return this.responses.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().toJson()));
    }
}
