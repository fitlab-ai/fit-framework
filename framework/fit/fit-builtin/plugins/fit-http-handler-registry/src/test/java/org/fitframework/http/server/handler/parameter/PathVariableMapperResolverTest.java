// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.fitframework.http.annotation.PathVariable;
import org.fitframework.http.annotation.RequestParam;
import org.fitframework.http.server.handler.PropertyValueMapper;
import org.fitframework.ioc.annotation.AnnotationMetadata;
import org.fitframework.ioc.annotation.AnnotationMetadataResolver;
import org.fitframework.util.ReflectionUtils;
import org.fitframework.value.PropertyValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Optional;

/**
 * 表示 {@link PathVariableMapperResolver} 的单元测试。
 *
 * @author 白鹏坤
 * @since 2023-02-27
 */
@DisplayName("测试 PathVariableMapperResolver 类")
class PathVariableMapperResolverTest {
    private final AnnotationMetadataResolver annotationResolver = mock(AnnotationMetadataResolver.class);
    private final PathVariableMapperResolver variableMapperResolver =
            new PathVariableMapperResolver(this.annotationResolver);

    @Test
    @DisplayName("当提供参数时，返回路径变量的映射器")
    void givenParamThenReturnPathVariableMapper() {
        final Parameter parameter =
                ReflectionUtils.getDeclaredMethod(HttpParamTest.class, "pathVariable", String.class).getParameters()[0];
        final AnnotationMetadata annotations = mock(AnnotationMetadata.class);
        final RequestParam requestParam = Mockito.mock(RequestParam.class);
        when(annotations.getAnnotation(any())).thenReturn(requestParam);
        when(requestParam.required()).thenReturn(true);
        when(requestParam.name()).thenReturn("v1");
        when(requestParam.defaultValue()).thenReturn(null);
        final Optional<PropertyValueMapper> parameterMapper =
                this.variableMapperResolver.resolve(PropertyValue.createParameterValue(parameter), annotations);
        assertThat(parameterMapper).isPresent();
    }

    @Test
    @DisplayName("获取需要解析的注解的类型")
    void shouldReturnAnnotation() {
        final Class<? extends Annotation> annotation = this.variableMapperResolver.getAnnotation();
        assertThat(annotation).isEqualTo(PathVariable.class);
    }
}
