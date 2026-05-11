// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.maven.complie.parser;

import org.fitframework.fel.tool.annotation.Group;
import org.fitframework.fel.tool.info.entity.DefinitionEntity;
import org.fitframework.fel.tool.info.entity.DefinitionGroupEntity;
import org.fitframework.fel.tool.info.entity.SchemaEntity;

import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * 提供对定义信息的解析。
 *
 * @author 曹嘉美
 * @author 杭潇
 * @since 2024-10-30
 */
public class ByteBuddyDefinitionsParser {
    /**
     * 解析定义组。
     *
     * @param typeDescription 表示类型的描述的 {@link TypeDescription}。
     * @return 表示定义组实体的 {@link DefinitionGroupEntity}。
     */
    public static DefinitionGroupEntity parseDefinitionGroup(TypeDescription typeDescription) {
        DefinitionGroupEntity definitionGroupEntity = new DefinitionGroupEntity();
        AnnotationDescription.Loadable<Group> groupAnnotation =
                typeDescription.getDeclaredAnnotations().ofType(Group.class);
        if (groupAnnotation == null) {
            return definitionGroupEntity;
        }
        Group group = groupAnnotation.load();
        definitionGroupEntity.setName(group.name());
        definitionGroupEntity.setSummary(group.summary());
        definitionGroupEntity.setDescription(group.description());
        definitionGroupEntity.setExtensions(ParserUtils.parseAttributes(group.extensions()));
        List<DefinitionEntity> definitionEntities = new LinkedList<>();
        for (MethodDescription.InDefinedShape methodDescription : typeDescription.getDeclaredMethods()) {
            Optional<DefinitionEntity> definitionEntity = parseDefinition(methodDescription);
            definitionEntity.ifPresent(definitionEntities::add);
        }
        definitionGroupEntity.setDefinitions(definitionEntities);
        return definitionGroupEntity;
    }

    private static Optional<DefinitionEntity> parseDefinition(MethodDescription.InDefinedShape methodDescription) {
        Optional<SchemaEntity> schemaEntity = ByteBuddySchemaParser.parseMethodSchema(methodDescription);
        if (schemaEntity.isEmpty()) {
            return Optional.empty();
        }
        DefinitionEntity definitionEntity = new DefinitionEntity();
        definitionEntity.setSchema(schemaEntity.get());
        return Optional.of(definitionEntity);
    }
}
