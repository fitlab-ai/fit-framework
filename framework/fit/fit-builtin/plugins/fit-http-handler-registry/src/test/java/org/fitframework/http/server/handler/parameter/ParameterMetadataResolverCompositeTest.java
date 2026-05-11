// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.fitframework.http.server.handler.PropertyValueMetadata;
import org.fitframework.http.server.handler.PropertyValueMetadataResolver;
import org.fitframework.ioc.annotation.AnnotationMetadata;
import org.fitframework.ioc.annotation.AnnotationMetadataResolver;
import org.fitframework.util.ReflectionUtils;
import org.fitframework.value.PropertyValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Parameter;
import java.util.List;

/**
 * 表示 {@link PropertyValueMetadataResolverComposite} 的单元测试。
 *
 * @author 白鹏坤
 * @since 2023-02-27
 */
@DisplayName("测试 ParameterMetadataResolverComposite 类")
class ParameterMetadataResolverCompositeTest {
    private final AnnotationMetadataResolver annotationResolver = mock(AnnotationMetadataResolver.class);
    private final Parameter parameter =
            ReflectionUtils.getDeclaredMethod(HttpParamTest.class, "pathVariable", String.class).getParameters()[0];

    @Test
    @DisplayName("当元数据处理器集合都不能处理请求参数时，返回 Optional 的空对象")
    void givenCanNotResolvedParameterThenReturnEmpty() {
        final AnnotationMetadata annotationMetadata = mock(AnnotationMetadata.class);
        when(this.annotationResolver.resolve(this.parameter)).thenReturn(annotationMetadata);
        when(annotationMetadata.isAnnotationPresent(any())).thenReturn(false);
        final RequestBodyMetadataResolver bodyMetadataResolver =
                new RequestBodyMetadataResolver(this.annotationResolver);
        final PropertyValueMetadataResolver[] resolvers = {bodyMetadataResolver};
        final PropertyValueMetadataResolverComposite resolverComposite =
                new PropertyValueMetadataResolverComposite(resolvers);
        final List<PropertyValueMetadata> resolved =
                resolverComposite.resolve(PropertyValue.createParameterValue(this.parameter));
        assertThat(resolved.size()).isEqualTo(0);
    }
}
