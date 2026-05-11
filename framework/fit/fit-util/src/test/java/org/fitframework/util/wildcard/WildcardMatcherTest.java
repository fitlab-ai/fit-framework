// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.util.wildcard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("测试通配符匹配算法")
class WildcardMatcherTest {
    @ParameterizedTest
    @DisplayName("当匹配成功时，返回 true")
    @CsvSource({"hello,*llo", "hello,he*", "hello,h*l*o", "hello,*h*e*l*l*o*", "hello,h?l?o"})
    void should_return_true_when_matched(String value, String pattern) {
        boolean ret = Pattern.forCharSequence(pattern).matches(value);
        assertTrue(ret);
    }

    @ParameterizedTest
    @DisplayName("当匹配失败时，返回 false")
    @CsvSource({"root.txt,fit-*.txt", "root.txt,r?t.txt"})
    void should_return_false_when_not_matched(String value, String pattern) {
        boolean ret = Pattern.forCharSequence(pattern).matches(value);
        assertFalse(ret);
    }
}
