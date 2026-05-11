// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.fewshot;

/**
 * 提示词例子的接口定义。
 *
 * @author 易文渊
 * @since 2024-04-25
 */
public interface Example {
    /**
     * 获取例子的问题。
     *
     * @return 返回表示问题的 {@link String}。
     */
    String question();

    /**
     * 获取例子的回答。
     *
     * @return 返回表示回答的 {@link String}。
     */
    String answer();
}