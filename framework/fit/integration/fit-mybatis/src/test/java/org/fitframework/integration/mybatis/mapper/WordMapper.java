// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.integration.mybatis.mapper;

import org.fitframework.integration.mybatis.model.WordDo;

/**
 * {@link WordDo} 的测试用数据库持久化层。
 *
 * @author 李金绪
 * @since 2025-02-25
 */
public interface WordMapper {
    /**
     * 添加单词。
     *
     * @param word 表示单词的 {@link WordDo}。
     */
    void add(WordDo word);

    /**
     * 获取单词。
     *
     * @param name 表示单词名称的 {@link String}。
     * @return 表示单词的 {@link WordDo}。
     */
    WordDo get(String name);
}