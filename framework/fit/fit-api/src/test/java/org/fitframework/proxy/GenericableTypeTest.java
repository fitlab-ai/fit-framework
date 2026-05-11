// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.proxy;

import static org.assertj.core.api.Assertions.assertThat;

import org.fitframework.broker.GenericableType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * {@link GenericableType} 的单元测试。
 *
 * @author 郭龙飞
 * @since 2023-02-24
 */
@DisplayName("测试 GenericableType 类以及相关类")
class GenericableTypeTest {
    @Test
    @DisplayName("提供 GenericableType 类 fromCode 方法时，返回正常信息")
    void givenGenericableTypeShouldReturnType() {
        Optional<GenericableType> genericableType = GenericableType.fromCode(GenericableType.API.code());
        assertThat(genericableType).isPresent().get().isEqualTo(GenericableType.API);
    }

    @Test
    @DisplayName("提供 GenericableType 类 code 不存在时，返回空")
    void givenGenericableTypeCodeWhenNotExistThenReturnEmpty() {
        Optional<GenericableType> genericableType = GenericableType.fromCode("221");
        assertThat(genericableType).isNotPresent();
    }
}