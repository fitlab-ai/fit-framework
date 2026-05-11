// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.integration.mybatis;

import static org.assertj.core.api.Assertions.assertThat;

import org.fitframework.integration.mybatis.mapper.WordMapper;
import org.fitframework.integration.mybatis.model.WordDo;
import org.fitframework.annotation.Fit;
import org.fitframework.conf.Config;
import org.fitframework.test.annotation.MybatisTest;
import org.fitframework.test.annotation.Sql;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 测试数据库层。
 *
 * @author 李金绪
 * @since 2025-02-25
 */
@MybatisTest(classes = {WordMapper.class})
@Sql(before = "sql/create/word.sql")
@DisplayName("测试自动转换驼峰形式")
public class WordMapperTest {
    @Fit
    private WordMapper mapper;
    @Fit
    private Config config;

    @Test
    @DisplayName("测试配置打开时,下划线正确转换至驼峰")
    void shouldOkWhenMapUnderscoreToCamelcase() {
        WordDo oriWord = this.mapper.get("hello");
        assertThat(oriWord).isNull();

        this.mapper.add(new WordDo("hello", "h"));

        WordDo curWord = this.mapper.get("hello");
        assertThat(curWord.getFirstLetter()).isEqualTo("h");
    }
}