// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.fitframework.conf.support.MapConfig;
import org.fitframework.ioc.BeanMetadata;
import org.fitframework.ioc.lifecycle.bean.ValueSupplier;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link ConfigDependencyRequirement} 的单元测试
 *
 * @author 郭龙飞
 * @since 2023-03-03
 */
@DisplayName("测试 ConfigDependencyRequirement 类")
class ConfigDependencyRequirementTest {
    @Test
    @DisplayName("提供 ConfigDependencyRequirement 类 empty 方法时，返回 true")
    void givenBeanResolverCompositeShouldReturnTrue() {
        BeanMetadata source = mock(BeanMetadata.class);
        MapConfig config = new MapConfig("m", null);
        when(source.config()).thenReturn(config);
        String expected = "111";
        config.set("value", expected);
        String expression = "${value:\"111\"}";
        ConfigDependencyRequirement requirement = new ConfigDependencyRequirement(source, expression);
        ValueSupplier supplier = requirement.withType(String.class, null);
        Object actual = supplier.get();
        assertThat(actual).isEqualTo(expected);
    }
}
