// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.fitframework.exception.ObjectInstantiationException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * {@link ObjectInstantiationException} 的单元测试。
 *
 * @author 季聿阶
 * @since 2022-02-18
 */
@DisplayName("验证 ObjectInstantiationException")
public class ObjectInstantiationExceptionTest {
    @Nested
    @DisplayName("验证创建一个新的异常")
    class TestNew {
        @Test
        @DisplayName("使用异常信息初始化异常，得到一个正确的异常")
        void givenMessageThenReturnCorrectException() {
            ObjectInstantiationException exception = new ObjectInstantiationException("message");
            assertThat(exception).hasMessage("message");
        }
    }
}
