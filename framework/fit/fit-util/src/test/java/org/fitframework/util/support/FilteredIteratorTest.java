// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.util.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.fitframework.util.CollectionUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * {@link FilteredIterator} 的单元测试。
 *
 * @author 白鹏坤
 * @since 2023-02-14
 */
@DisplayName("测试 FilteredIterator 类")
class FilteredIteratorTest {
    private final Iterator<Integer> numberIterator = CollectionUtils.iterator(Arrays.asList(1, 2, 3));
    private FilteredIterator<Integer> iterator;

    @BeforeEach
    void setUp() {
        Predicate<Integer> predicate = number -> number >= 2;
        this.iterator = new FilteredIterator<>(this.numberIterator, predicate);
    }

    @Test
    @DisplayName("当提供过滤后的迭代器进行迭代时，返回迭代元素")
    void givenFilteredIteratorThenReturnElement() {
        final List<Integer> list = new ArrayList<>();
        while (this.iterator.hasNext()) {
            final Integer next = this.iterator.next();
            list.add(next);
        }
        assertThat(list).contains(2, 3);
    }

    @Test
    @DisplayName("当下一个迭代元素为 null 时，抛出异常")
    void givenNextIsNullThenThrowException() {
        FilteredIterator<?> emptyIterator = new FilteredIterator<>(Collections.emptyIterator(), (ele) -> false);
        assertThatThrownBy(emptyIterator::next).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("应该返回没过滤前的迭代器")
    void shouldReturnOriginIterator() {
        final Iterator<Integer> origin = this.iterator.origin();
        assertThat(origin).isEqualTo(this.numberIterator);
    }
}
