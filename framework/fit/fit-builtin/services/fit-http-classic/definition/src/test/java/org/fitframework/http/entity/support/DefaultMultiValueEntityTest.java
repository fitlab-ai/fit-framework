// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.entity.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.fitframework.http.server.handler.MockHttpClassicServerRequest;
import org.fitframework.http.server.support.DefaultHttpClassicServerRequest;
import org.fitframework.model.MultiValueMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

/**
 * 为 {@link DefaultMultiValueEntity} 提供单元测试。
 *
 * @author 白鹏坤
 * @since 2023-02-22
 */
@DisplayName("测试 DefaultMultiValueEntity 类")
class DefaultMultiValueEntityTest {
    private DefaultMultiValueEntity valueEntity;

    @BeforeEach
    void setup() {
        final MultiValueMap<String, String> multiValueMap = MultiValueMap.create();
        multiValueMap.add("k1", "v1");
        multiValueMap.add("k1", "v11");
        DefaultHttpClassicServerRequest request = new MockHttpClassicServerRequest().getRequest();
        this.valueEntity = new DefaultMultiValueEntity(request, multiValueMap);
    }

    @Test
    @DisplayName("获取所有的键的列表")
    void shouldReturnEntityKeys() {
        final List<String> keys = this.valueEntity.keys();
        assertThat(keys).hasSize(1).containsSequence("k1");
    }

    @Test
    @DisplayName("获取指定键的第一个值")
    void shouldReturnEntityFirstValue() {
        final Optional<String> first = this.valueEntity.first("k1");
        assertThat(first).isPresent().get().isEqualTo("v1");
    }

    @Test
    @DisplayName("获取所有的数据对的数量")
    void shouldReturnEntitySize() {
        final int size = this.valueEntity.size();
        assertThat(size).isEqualTo(1);
    }

    @Test
    @DisplayName("获取所有的数据对的数量")
    void shouldReturnEntityAllValues() {
        final List<String> all = this.valueEntity.all("k1");
        assertThat(all).hasSize(2).containsSequence("v1", "v11");
    }
}
