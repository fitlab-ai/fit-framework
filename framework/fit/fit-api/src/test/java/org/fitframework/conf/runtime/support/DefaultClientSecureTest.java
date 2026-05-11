// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.conf.runtime.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link DefaultClientSecure} 的测试类。
 *
 * @author 李金绪
 * @since 2024-08-22
 */
@DisplayName("测试 DefaultClientSecure")
public class DefaultClientSecureTest {
    @Test
    @DisplayName("测试正确创建实例")
    void shouldCreateInstance() {
        DefaultClientSecure clientSecure = new DefaultClientSecure();
        clientSecure.setSecureRandomEnabled(true);
        clientSecure.setSecureProtocol("TLSv1.2");
        Assertions.assertTrue(clientSecure.secureRandomEnabled());
        Assertions.assertEquals("TLSv1.2", clientSecure.secureProtocol().get());
    }
}
