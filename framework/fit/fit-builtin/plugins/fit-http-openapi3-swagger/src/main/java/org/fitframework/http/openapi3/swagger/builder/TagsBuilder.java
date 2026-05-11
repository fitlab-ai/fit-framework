// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.openapi3.swagger.builder;

import org.fitframework.http.openapi3.swagger.EntityBuilder;
import org.fitframework.http.openapi3.swagger.entity.Tag;
import org.fitframework.http.server.HttpDispatcher;
import org.fitframework.http.server.HttpHandlerGroup;
import org.fitframework.ioc.BeanContainer;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 表示 {@link List}{@code <}{@link Tag}{@code >} 的构建器。
 *
 * @author 季聿阶
 * @since 2023-08-23
 */
public class TagsBuilder extends AbstractBuilder implements EntityBuilder<List<Tag>> {
    TagsBuilder(BeanContainer container) {
        super(container);
    }

    @Override
    public List<Tag> build() {
        Collection<HttpHandlerGroup> groups = this.getHttpDispatcher()
                .map(HttpDispatcher::getHttpHandlerGroups)
                .map(Map::values)
                .orElseGet(Collections::emptyList);
        return groups.stream()
                .filter(group -> group.getHandlers().stream().anyMatch(handler -> !this.isHandlerIgnored(handler)))
                .map(group -> Tag.custom().name(group.getName()).description(group.getDescription()).build())
                .collect(Collectors.toList());
    }
}
