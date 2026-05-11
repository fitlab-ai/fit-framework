// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.openapi3.swagger;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.annotation.DocumentIgnored;
import org.fitframework.http.annotation.GetMapping;
import org.fitframework.http.openapi3.swagger.builder.OpenApiBuilder;
import org.fitframework.annotation.Component;
import org.fitframework.ioc.BeanContainer;

import java.util.Map;

/**
 * 表示 OpenAPI 文档的控制器。
 *
 * @author 季聿阶
 * @since 2023-08-15
 */
@Component
public class DocumentController {
    private final OpenApiBuilder openApiBuilder;

    public DocumentController(BeanContainer container) {
        this.openApiBuilder = new OpenApiBuilder(notNull(container, "The bean container cannot be null."));
    }

    /**
     * 获取当前进程所有 REST 接口的 OpenAPI 文档。
     *
     * @return 表示当前进程所有 REST 接口的 OpenAPI 文档的 {@link Map}{@code <}{@link String}{@code , }{@link
     * Object}{@code >}。
     */
    @DocumentIgnored
    @GetMapping("/v3/openapi")
    public Map<String, Object> getDocument() {
        return this.openApiBuilder.build().toJson();
    }
}
