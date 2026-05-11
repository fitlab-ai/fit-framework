// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.fewshot.support;

import static org.fitframework.inspection.Validation.notBlank;

import org.fitframework.fel.core.fewshot.Example;

/**
 * 表示 {@link Example} 的默认实现。
 *
 * @author 易文渊
 * @since 2024-04-25
 */
public class DefaultExample implements Example {
    private final String question;
    private final String answer;

    /**
     * 使用问答对创建 {@link DefaultExample} 的实例。
     *
     * @param question 表示问题的 {@link String}。
     * @param answer 表示答案的 {@link String}。
     * @throws IllegalArgumentException 当 {@code question}、{@code answer} 为 {@code null} 时。
     */
    public DefaultExample(String question, String answer) {
        this.question = notBlank(question, "The question cannot be blank.");
        this.answer = notBlank(answer, "The answer cannot be blank.");
    }

    @Override
    public String question() {
        return this.question;
    }

    @Override
    public String answer() {
        return this.answer;
    }
}