// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.globalization;

import java.util.Locale;
import java.util.Map;

/**
 * 为应用程序的国际化提供字符串管理能力。
 *
 * @author 梁济时
 * @since 2022-11-21
 */
public interface StringResource {
    /**
     * 获取在指定语言环境中使用的指定键的字符串资源。
     *
     * @param locale 表示目标语言环境的 {@link Locale}。
     * @param key 表示资源的键的 {@link String}。
     * @param args 表示资源的格式化参数的 {@link Object}{@code []}。
     * @return 表示资源的值的 {@link String}。
     */
    default String getMessage(Locale locale, String key, Object... args) {
        return this.getMessageWithDefault(locale, key, null, args);
    }

    /**
     * 获取在指定语言环境中使用的指定键的字符串资源。
     *
     * @param locale 表示目标语言环境的 {@link Locale}。
     * @param key 表示资源的键的 {@link String}。
     * @param defaultMessage 表示资源的默认值的 {@link String}。
     * @param args 表示资源的格式化参数的 {@link Object}{@code []}。
     * @return 表示资源的值的 {@link String}。
     */
    String getMessageWithDefault(Locale locale, String key, String defaultMessage, Object... args);

    /**
     * 获取在指定语言环境中使用的指定键的字符串资源。
     *
     * @param locale 表示目标语言环境的 {@link Locale}。
     * @param key 表示资源的键的 {@link String}。
     * @param args 表示资源的格式化参数的 {@link Map}{@code <}{@link String}{@code , }{@link Object}{@code >}。
     * @return 表示资源的值的 {@link String}。
     */
    default String getMessage(Locale locale, String key, Map<String, Object> args) {
        return this.getMessageWithDefault(locale, key, null, args);
    }

    /**
     * 获取在指定语言环境中使用的指定键的字符串资源。
     *
     * @param locale 表示目标语言环境的 {@link Locale}。
     * @param key 表示资源的键的 {@link String}。
     * @param defaultMessage 表示资源的默认值的 {@link String}。
     * @param args 表示资源的格式化参数的 {@link Map}{@code <}{@link String}{@code , }{@link Object}{@code >}。
     * @return 表示资源的值的 {@link String}。
     */
    String getMessageWithDefault(Locale locale, String key, String defaultMessage, Map<String, Object> args);
}
