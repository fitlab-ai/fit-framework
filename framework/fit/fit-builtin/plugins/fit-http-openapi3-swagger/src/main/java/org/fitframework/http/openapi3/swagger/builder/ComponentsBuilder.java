// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.openapi3.swagger.builder;

import org.fitframework.http.openapi3.swagger.EntityBuilder;
import org.fitframework.http.openapi3.swagger.entity.Components;
import org.fitframework.http.openapi3.swagger.entity.Schema;
import org.fitframework.ioc.BeanContainer;

import java.util.Map;

/**
 * 表示 {@link Components} 的构建器。
 *
 * @author 季聿阶
 * @since 2023-08-28
 */
public class ComponentsBuilder extends AbstractBuilder implements EntityBuilder<Components> {
    private final SchemasBuilder schemasBuilder;

    ComponentsBuilder(BeanContainer container) {
        super(container);
        this.schemasBuilder = new SchemasBuilder(container);
    }

    @Override
    public Components build() {
        Map<String, Schema> schemas = this.schemasBuilder.build();
        return Components.custom().schemas(schemas).build();
    }
}
