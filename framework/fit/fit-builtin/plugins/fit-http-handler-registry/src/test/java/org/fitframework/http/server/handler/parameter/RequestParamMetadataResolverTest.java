// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.fitframework.http.annotation.RequestParam;
import org.fitframework.http.server.handler.PropertyValueMetadata;
import org.fitframework.ioc.annotation.AnnotationMetadata;
import org.fitframework.ioc.annotation.AnnotationMetadataResolver;
import org.fitframework.util.ReflectionUtils;
import org.fitframework.value.PropertyValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.List;

/**
 * 表示 {@link RequestParamMetadataResolver} 的单元测试。
 *
 * @author 白鹏坤
 * @since 2023-02-24
 */
@DisplayName("测试 RequestParamMetadataResolver 类")
class RequestParamMetadataResolverTest {
    private final AnnotationMetadataResolver annotationResolver = mock(AnnotationMetadataResolver.class);
    private final RequestParamMetadataResolver metadataResolver =
            new RequestParamMetadataResolver(this.annotationResolver);

    @Test
    @DisplayName("当提供参数时，返回参数中的元信息")
    void givenParamThenReturnParameterMetadata() {
        final Parameter parameter =
                ReflectionUtils.getDeclaredMethod(HttpParamTest.class, "requestParam", String.class).getParameters()[0];
        final AnnotationMetadata annotations = mock(AnnotationMetadata.class);
        final RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
        when(annotations.getAnnotation(RequestParam.class)).thenReturn(requestParam);
        final List<PropertyValueMetadata> propertyValueMetadata =
                this.metadataResolver.resolve(PropertyValue.createParameterValue(parameter), annotations);
        assertThat(propertyValueMetadata.size()).isEqualTo(1);
        assertThat(propertyValueMetadata.get(0)).returns("p1", PropertyValueMetadata::name);
    }

    @Test
    @DisplayName("获取需要解析的注解的类型")
    void shouldReturnAnnotation() {
        final Class<? extends Annotation> annotation = this.metadataResolver.getAnnotation();
        assertThat(annotation).isEqualTo(RequestParam.class);
    }
}
