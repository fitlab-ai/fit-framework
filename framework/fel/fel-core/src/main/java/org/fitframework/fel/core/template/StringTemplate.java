// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.template;

import org.fitframework.fel.core.template.support.DefaultStringTemplate;

import java.util.Map;

/**
 * 字符串模板接口定义。
 *
 * @author 易文渊
 * @since 2024-04-27
 */
public interface StringTemplate extends GenericTemplate<Map<String, String>, String> {
    /**
     * 创建一个默认的字符串模板实例。
     *
     * @param template 表示使用 mustache 模板语法的 {@link String}。
     * @return 表示字符串模板的 {@link StringTemplate}。
     */
    static StringTemplate create(String template) {
        return new DefaultStringTemplate(template);
    }
}