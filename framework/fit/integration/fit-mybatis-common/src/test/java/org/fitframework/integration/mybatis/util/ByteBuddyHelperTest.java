// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.integration.mybatis.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 为 {@link ByteBuddyHelper} 提供单元测试。
 *
 * @author 季聿阶
 * @since 2024-08-02
 */
@DisplayName("测试 ByteBuddyHelper")
public class ByteBuddyHelperTest {
    @Test
    @DisplayName("测试依赖中包含 byte-buddy，则 byte-buddy 的动态代理能力可用")
    void byteBuddyIsAvailable() {
        boolean actual = ByteBuddyHelper.isByteBuddyAvailable();
        assertThat(actual).isTrue();
    }
}
