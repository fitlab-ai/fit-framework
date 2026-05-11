// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.jvm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import org.fitframework.jvm.classfile.AccessFlag;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link Modifier} 的单元测试。
 *
 * @author 郭龙飞
 * @since 2023-02-02
 */
@DisplayName("测试 Modifier 类")
class ModifierTest {
    @Test
    @DisplayName("测试 Modifier 类 describe 方法，返回描述信息")
    void givenModifierThenDescribe() {
        Modifier modifier = Modifier.of(AccessFlag.ACC_PRIVATE);
        String describe = modifier.describe();
        assertThat(describe).isEqualTo("private");
    }

    @Test
    @DisplayName("提供 Modifier 类不存在的修饰类型时，抛出异常信息")
    void givenModifierThenThrowException() {
        AccessFlag mock = mock(AccessFlag.class);
        assertThatThrownBy(() -> Modifier.of(mock)).isInstanceOf(IllegalArgumentException.class);
    }
}