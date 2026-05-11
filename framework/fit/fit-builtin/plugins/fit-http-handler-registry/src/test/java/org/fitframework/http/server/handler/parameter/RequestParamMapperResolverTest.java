// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.fitframework.http.annotation.RequestParam;
import org.fitframework.http.annotation.RequestQuery;
import org.fitframework.http.server.handler.PropertyValueMapper;
import org.fitframework.ioc.annotation.AnnotationMetadata;
import org.fitframework.ioc.annotation.AnnotationMetadataResolver;
import org.fitframework.util.ReflectionUtils;
import org.fitframework.value.PropertyValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Optional;

/**
 * 表示 {@link AbstractRequestParamMapperResolver} 的单元测试。
 *
 * @author 白鹏坤
 * @since 2023-02-24
 */
@DisplayName("测试 RequestParamMapperResolver 类")
class RequestParamMapperResolverTest {
    private final AnnotationMetadataResolver annotationResolver = mock(AnnotationMetadataResolver.class);
    private final AbstractRequestParamMapperResolver metadataResolver =
            new RequestQueryMapperResolver(this.annotationResolver);

    @Test
    @DisplayName("通过注解解析器来实例化参数映射器")
    void givenParamThenReturnParameterMapper() {
        final Parameter parameter =
                ReflectionUtils.getDeclaredMethod(HttpParamTest.class, "requestParam", String.class).getParameters()[0];
        final AnnotationMetadata annotations = mock(AnnotationMetadata.class);
        final RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
        when(annotations.getAnnotation(any())).thenReturn(requestParam);
        final Optional<PropertyValueMapper> parameterMapper =
                this.metadataResolver.resolve(PropertyValue.createParameterValue(parameter), annotations);
        assertThat(parameterMapper).isPresent();
    }

    @Test
    @DisplayName("获取需要解析的注解的类型")
    void shouldReturnAnnotation() {
        final Class<? extends Annotation> annotation = this.metadataResolver.getAnnotation();
        assertThat(annotation).isEqualTo(RequestQuery.class);
    }
}
