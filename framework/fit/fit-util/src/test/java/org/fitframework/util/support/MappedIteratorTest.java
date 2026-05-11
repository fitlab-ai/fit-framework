// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.util.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;

class MappedIteratorTest {
    @Test
    void should_return_mapped_values() {
        Iterator<Integer> origin = Arrays.asList(1, 2).iterator();
        Iterator<String> mapped = new MappedIterator<>(origin, Object::toString);
        assertTrue(mapped.hasNext());
        assertEquals("1", mapped.next());
        assertTrue(mapped.hasNext());
        assertEquals("2", mapped.next());
        assertFalse(mapped.hasNext());
    }
}
