// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.fitframework.ioc.BeanApplicableScope;
import org.fitframework.ioc.BeanDefinition;
import org.fitframework.ioc.annotation.AnnotationMetadata;
import org.fitframework.util.MapBuilder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * {@link DefaultBeanDefinition} 的单元测试。
 *
 * @author 郭龙飞
 * @since 2023-02-24
 */
@DisplayName("测试 DefaultBeanDefinition 类")
class DefaultBeanDefinitionTest {
    @Test
    @DisplayName("提供 DefaultBeanDefinition 类时，返回正常信息")
    void givenDefaultBeanDefinitionShouldReturnMessage() {
        String name = "11";
        Set<String> emptySet = Collections.emptySet();
        String stereotype = "22";
        Map<String, Object> properties = MapBuilder.<String, Object>get().put("33", 44).build();
        AnnotationMetadata annotations = mock(AnnotationMetadata.class);
        Type superType = this.getClass().getGenericSuperclass();
        DefaultBeanDefinition definition = new DefaultBeanDefinition(name, superType, emptySet, stereotype,
            annotations, false, false, emptySet, BeanApplicableScope.ANYWHERE, properties);
        BeanDefinition build = new DefaultBeanDefinition.Builder(definition)
                .set("", "")
                .name(name)
                .type(superType)
                .aliases(emptySet)
                .stereotype(stereotype)
                .annotations(annotations)
                .applicable(BeanApplicableScope.ANYWHERE)
                .lazy(false)
                .dependencies(emptySet)
                .preferred(false)
                .build();
        assertThat(definition.toString()).isEqualTo(build.toString());
    }
}