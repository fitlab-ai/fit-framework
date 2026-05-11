// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.parameterization.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.fitframework.parameterization.ResolvedParameter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link DefaultResolvedParameter} 的单元测试。
 *
 * @author 季聿阶
 * @since 2022-02-17
 */
@DisplayName("验证 DefaultResolvedParameter")
public class DefaultResolvedParameterTest {
    @Test
    @DisplayName("创建一个解析过的变量信息，得到一个正确的解析过的变量信息")
    void givenResolvedValuesThenReturnCorrectResult() {
        ResolvedParameter actual = new DefaultResolvedParameter("value", 0, 5, 6);
        assertThat(actual.getName()).isEqualTo("value");
        assertThat(actual.getPosition()).isEqualTo(0);
        assertThat(actual.toString()).isEqualTo("[position=0, variable=value]");
    }
}
