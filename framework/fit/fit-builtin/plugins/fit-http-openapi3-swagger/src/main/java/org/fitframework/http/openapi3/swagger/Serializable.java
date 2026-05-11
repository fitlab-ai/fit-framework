// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.openapi3.swagger;

import java.util.Map;

/**
 * 表示可序列化为 JSON 对象的接口。
 *
 * @author 季聿阶
 * @since 2023-08-23
 */
public interface Serializable {
    /**
     * 将当前对象转换成 JSON 格式。
     *
     * @return 表示 JSON 格式的 {@link Map}{@code <}{@link String}{@code , }{@link Object}{@code >}。
     */
    Map<String, Object> toJson();
}
