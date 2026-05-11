// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.util.wildcard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

@DisplayName("测试空的符号序")
class SymbolSequenceEmptySymbolSequenceTest {
    @Test
    @DisplayName("空字符序的长度为 0")
    void should_return_zero_when_fetch_length() {
        int length = SymbolSequence.empty().length();
        assertEquals(0, length);
    }

    @Test
    @DisplayName("通过索引获取符号时抛出异常")
    void should_throw_when_fetch_element_by_index() {
        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class,
                () -> SymbolSequence.empty().at(0));
        assertEquals("The index to lookup element in sequence is out of bounds. [index=0, length=0]",
                exception.getMessage());
    }

    @Test
    @DisplayName("返回的迭代器中不包含任何元素")
    void should_return_empty_iterator() {
        Iterator<?> iterator = SymbolSequence.empty().iterator();
        assertNotNull(iterator);
        assertFalse(iterator.hasNext());
    }
}
